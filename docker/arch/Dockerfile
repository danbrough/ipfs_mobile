FROM archlinux
RUN pacman -Syu --noconfirm && pacman -S --noconfirm \
    jdk11-openjdk \
    base-devel \
    git \
    nano \
    pkgconf \
    unzip \
    wget

#RUN apt-get update && apt-get install -y \
#    build-essential \
#    default-jdk-headless \
#   git \
#    libssl-dev \
#    nano \
#    pkg-config \
#    unzip \
#    wget
COPY root /
#ENTRYPOINT /setup.sh


