package ipfs

import (
  config "github.com/ipfs/go-ipfs-config"
  "github.com/ipfs/go-ipfs/repo/fsrepo"
)

func LoadConfig(path string) (*config.Config, error) {
  return fsrepo.ConfigAt(path)
}
