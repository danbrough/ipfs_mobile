#
# ~/.bashrc
#




# If not running interactively, don't do anything
#[[ $- != *i* ]] && return

alias ls='ls --color=auto'
export PS1='\[\033[00;36m\]\u@\h\[\033[01;32m\] \w \$\[\033[00m\] '

source ~/ipfs_mobile/docker/env.sh


