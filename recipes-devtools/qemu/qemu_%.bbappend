# This removes imx-gpu-viv dependency
# If unacceptable, need to configure libsdl to exclude wayland
PACKAGECONFIG_remove_mx6-evobb = "sdl kvm"
