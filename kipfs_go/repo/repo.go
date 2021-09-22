package repo

import (
   "github.com/ipfs/go-ipfs/repo/fsrepo"
)

func RepoIsInitialized(path string) bool {
  return fsrepo.IsInitialized(path)
}
