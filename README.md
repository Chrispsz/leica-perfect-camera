# Leica Perfect — PhotonCamera Fork

Fork do [PhotonCamera](https://github.com/bjzhou/PhotonCamera) (upstream `bjzhou/PhotonCamera` tag `1.26.1`) com 67 patches cirúrgicos que produzem o APK **Leica Perfect v6.4.0-fix11** (Cron 19 MAX-ONLY).

## O que este fork faz

- **mode_max como único modo e padrão** — máxima qualidade sempre, sem Sport/Intelligent
- **Menu simplificado** — só seletor de LUT (5 looks curados), sem complexidade
- **JPEG one-click** — RAW/DNG export desativado, JPEG Q100 sempre
- **157 patches de processamento** — tone mapping, sharpening, NLM, HDR, AgX, filmic, Mertens
- **Cron 18 PERFECT-ULTIMATE** — 10/10 valores verificados no JSON

## Build via GitHub Actions

O workflow `.github/workflows/build.yml` compila o APK automaticamente:

1. Clona `bjzhou/PhotonCamera` tag 1.26.1
2. Aplica 67 patches (`build-archlinux.sh patch`)
3. Compila com Gradle (`build-archlinux.sh build`)
4. Upload do APK como artifact

**Trigger:** push na main ou manual via "Run workflow" na aba Actions.

## Build local

```bash
# Pré-requisitos: JDK 17, Android SDK (platforms;android-34, build-tools;34.0.0)
export ANDROID_HOME=/opt/android-sdk

./build-archlinux.sh clone    # clona upstream
./build-archlinux.sh patch    # aplica 67 patches
./build-archlinux.sh build    # compila APK debug
# ou:
./build-archlinux.sh all      # faz tudo
```

## Estrutura

```
├── build-archlinux.sh          # Build script (clone + patch + build)
├── config/
│   └── leica_perfect.json      # Config Cron 18 (active_capture_mode=mode_max)
├── patches/
│   ├── LeicaConfig.kt          # Config loader (lê leica_perfect.json)
│   ├── LeicaRuntimeState.kt    # Runtime state (capture mode override)
│   ├── LeicaSettingsScreen.kt  # UI minimalista (só LUT picker)
│   ├── LeicaStateDumper.kt     # Debug state dumper
│   ├── LeicaThermalMonitor.kt  # Thermal monitor
│   └── *.patch.kt              # Patches de processamento (AgX, NLM, HDR, etc)
└── .github/workflows/build.yml # GitHub Actions workflow
```

## Config

O `config/leica_perfect.json` controla TODOS os parâmetros de processamento:
- `active_capture_mode: "mode_max"` — único modo, sempre ativo
- Per-lens: gamma_shoulder, highlight_compression_ev, sharpening, NLM
- Filmic: dynamic_range, shoulder_power, toe_power
- Mertens: saturation_weight, contrast_weight

## Target device

Xiaomi 15T (dizi) — Snapdragon 8 Gen 3, 50MP main + 50MP tele + 12MP UW

## Licença

Upstream: GPL-3.0 (PhotonCamera by eszdman/bjzhou)
