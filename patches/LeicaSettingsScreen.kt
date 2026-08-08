package com.hinnka.mycamera.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * LeicaSettingsScreen — v6.4.0-fix12 ABSOLUTE
 *
 * Configuração absoluta. Sem menu, sem seletores, sem ajustes.
 * Tudo hardcoded no JSON (Cron 18 PERFECT-ULTIMATE):
 *   - mode_max sempre (único modo, default, sem cycling)
 *   - LUT: Leica M9 CCD (hardcoded no profile default)
 *   - Sharpening, NLM, HDR, AgX, filmic — todos valores Cron 18
 *   - JPEG Q100, HEIC Q100, UltraHDR Q100
 *   - Sem RAW/DNG export
 *
 * Esta screen existe só pra não quebrar referências de navegação.
 * Mostra info estática — nada ajustável.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeicaSettingsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color(0xFF0D0D0D),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Leica Perfect — Absoluto",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1A1A1A)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Quality Max — Ativo",
                color = Color(0xFF10B981),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Configuração absoluta. Sem ajustes.",
                color = Color(0x80FFFFFF),
                fontSize = 14.sp
            )
            Text(
                text = "v6.4.0-fix12 · Cron 18 PERFECT-ULTIMATE\n" +
                    "157 patches · mode_max · Leica M9 CCD\n" +
                    "JPEG Q100 · HEIC Q100 · UltraHDR Q100",
                color = Color(0x60FFFFFF),
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
        }
    }
}
