package com.hinnka.mycamera.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hinnka.mycamera.raw.LeicaConfig
import com.hinnka.mycamera.raw.LeicaRuntimeState

/**
 * ═══════════════════════════════════════════════════════════════════════════════
 * LeicaSettingsScreen — Menu do fork Leica Perfect
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * UI minimalista — mode_max é o único modo e já é o padrão.
 * Menu só tem seletor de LUT pra quem quiser mudar o look.
 *
 * v6.4.0-fix11 (Cron 19 MAX-ONLY):
 *   - mode_fast REMOVIDO — só mode_max (máxima qualidade sempre).
 *   - ONE-CLICK MAX button REMOVIDO (redundante — já é default).
 *   - Menu simplificado: só seletor de LUT.
 */

// ── 1 Capture Mode — Quality Max sempre ──────────────────────────────
private data class CaptureModeOption(
    val id: String,
    val label: String,
    val desc: String
)

private val CAPTURE_MODES = listOf(
    CaptureModeOption("mode_max", "Quality Max", "Máxima qualidade — padrão ativo")
)

// ── 5 Melhores LUTs — curados pra mod menu (v6.4.0) ──────────────────
private data class LutOption(
    val id: String,
    val label: String,
    val desc: String
)

private val BEST_LUTS = listOf(
    LutOption("leica_m9", "Leica M9 CCD", "Neutro quente — diário, street, documental"),
    LutOption("Hasselblad", "Hasselblad HNCS", "Natural — retrato premium, moda, produto"),
    LutOption("cc", "Fuji Classic Chrome", "Vintage saturação baixa — urbano, street"),
    LutOption("nc", "Fuji Classic Neg", "Quente golden hour — retrato, lifestyle"),
    LutOption("film_cinestill_800t", "CineStill 800T", "Túngsten — noite, neon, long exposure")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeicaSettingsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentCaptureMode by remember { mutableStateOf(LeicaConfig.activeCaptureMode) }
    var currentLutId by remember { mutableStateOf(LeicaConfig.runtimeLutOverride ?: LeicaConfig.activeLutId) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color(0xFF0D0D0D),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Leica Perfect",
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // ─── Capture Mode (informational — mode_max is the only option) ──
            item {
                Spacer(modifier = Modifier.height(8.dp))
                SectionHeader("Capture Mode")
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Quality Max",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "Máxima qualidade — sempre ativo",
                            color = Color(0x80FFFFFF),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // ─── 5 Melhores LUTs (v6.4.0) ────────────────────────────
            item {
                SectionHeader("Look (LUT)")
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        BEST_LUTS.forEachIndexed { idx, lut ->
                            val isSelected = currentLutId == lut.id
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        currentLutId = lut.id
                                        LeicaRuntimeState.setRuntimeLutOverride(lut.id)
                                    }
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                SelectionDot(isSelected)
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = lut.label,
                                        color = Color.White,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = lut.desc,
                                        color = Color(0x80FFFFFF),
                                        fontSize = 12.sp
                                    )
                                }
                            }
                            if (idx < BEST_LUTS.size - 1) {
                                androidx.compose.material3.Divider(color = Color.White.copy(alpha = 0.08f))
                            }
                        }
                        // ─── Reset option ─────────────────────────────
                        androidx.compose.material3.Divider(color = Color.White.copy(alpha = 0.08f))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    currentLutId = ""
                                    LeicaRuntimeState.setRuntimeLutOverride(null)
                                }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SelectionDot(currentLutId.isEmpty())
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Reset (usar profile default)",
                                color = Color(0x80FFFFFF),
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        color = Color(0xFFFF6B35),
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
    )
}

@Composable
private fun SelectionDot(isSelected: Boolean) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .background(
                if (isSelected) Color(0xFFFF6B35) else Color.Transparent,
                shape = androidx.compose.foundation.shape.CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}
