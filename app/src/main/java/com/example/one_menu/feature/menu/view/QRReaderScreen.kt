package com.example.one_menu.feature.menu.view

import android.Manifest
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.goodsaccounting.common.view.click.alphaClick
import com.example.one_menu.R
import com.example.one_menu.feature.nav.view.LocalBottomNavBarSetting
import kotlinx.coroutines.launch

@Composable
fun QRReaderScreen(
    openNotification: ()-> Unit,
    openInformationInstitution: ()->Unit,
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult ={  }
    )
    LaunchedEffect(Unit){
        launcher.launch( Manifest.permission.CAMERA)
    }
    val bottomNavBarSetting = LocalBottomNavBarSetting.current
    LaunchedEffect(Unit) {
        bottomNavBarSetting?.offPadding()
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        val codeScanner = rememberCodeScannerView(openInformationInstitution)
        AndroidView(
            factory = { codeScanner },
            modifier = Modifier.fillMaxSize()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
                .padding(horizontal = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(
                text = stringResource(R.string.find_your_favorite_food),
                fontSize = 31.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Box(
                modifier = Modifier
                    .alphaClick(onClick = openNotification)
                    .size(45.dp)
                    .background(Color.White.copy(0.3f), MaterialTheme.shapes.large),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_notifiaction),
                    contentDescription = null,
                    modifier = Modifier
                        .size(23.dp),
                )
            }
        }
        Text(
            text = stringResource(R.string.put_qr_code_in_frame),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .offset(y= (-160).dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            color = Color.White,
        )
    }
}

@Composable
fun rememberCodeScannerView(
    onQRCodeRead : ()-> Unit
) : CodeScannerView{
    val context = LocalContext.current
    val codeScannerView = remember { CodeScannerView(context).apply {
        isFlashButtonVisible = false
        isAutoFocusButtonVisible = false
        frameCornersRadius = 30
        frameCornersSize = 60
        frameColor = Color(0xFFFECE00).hashCode()
        frameSize = 0.7f
    } }
    val localCoroutine = rememberCoroutineScope()
    val codeScanner = remember(codeScannerView) {
        CodeScanner(context, codeScannerView).apply {
            camera = CodeScanner.CAMERA_BACK
            scanMode = ScanMode.CONTINUOUS
            isFlashEnabled = false
            decodeCallback = DecodeCallback {
                localCoroutine.launch {
                    onQRCodeRead()
                }
            }
        }
    }
    val lifeCycleObserver = rememberLifeCycleObserver(codeScanner)
    val lifeCycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifeCycle) {
        lifeCycle.addObserver(lifeCycleObserver)
        onDispose {
            lifeCycle.removeObserver(lifeCycleObserver)
        }
    }
    return codeScannerView
}
@Composable
private fun rememberLifeCycleObserver(codeScanner: CodeScanner): LifecycleEventObserver = remember(codeScanner) {
    LifecycleEventObserver {_, event ->
        when(event) {
            Lifecycle.Event.ON_RESUME -> codeScanner.startPreview()
            Lifecycle.Event.ON_PAUSE -> codeScanner.releaseResources()
            else ->{}
        }
    }
}