package com.example.count_out.entity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import javax.inject.Inject

class Internet @Inject constructor(val context: Context) {

    fun isOnline(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
}
@Composable
fun WarningNotInternet(titleId: Int, textId: Int){
    val openAlertDialog = remember { mutableStateOf(true) }
    if(openAlertDialog.value) {
        AlertDialog(
            title = { Text(text = stringResource(id = titleId)) },
            text = { Text(text = stringResource(id = textId)) },
            onDismissRequest = { openAlertDialog.value = false },
            confirmButton = { Text(text = "OK",
                modifier = Modifier.padding(horizontal = 18.dp).clickable { openAlertDialog.value = false })  }
        )
    }
}