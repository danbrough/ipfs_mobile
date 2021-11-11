package pubsub

import (
  "bufio"
  "kipfs/core"
)
import "kipfs/testing"

type SubscriptionListener interface {
  OnMessage(s string)
}

type PubSub struct {
}

type Subscription struct {
  Topic    string
  Listener SubscriptionListener
}

func (p *PubSub) NewSubscription(shell *core.Shell, topic string, listener SubscriptionListener) *Subscription {
  return &Subscription{topic, listener}
}

func (p *PubSub) Test() {
  testing.TestLog.Error("The test worke!@!d")
}

func Subscribe(shell *core.Shell, topic string, listener SubscriptionListener) {
  testing.TestLog.Error("Subscribe()")
  testing.TestLog.Warn("Subscribe() %s", topic)
  req := shell.NewRequest("pubsub/sub")
  req.Argument(topic)
  response, err := req.Send2()
  if err != nil {
    panic(err)
  }

  var doClose = func() {
    testing.TestLog.Warn("Closing response")
    err := response.Close()
    if err != nil {
      testing.TestLog.Error("Error closing response: %s", err)
    }
  }

  defer doClose()

  scanner := bufio.NewScanner(response.Output)
  // optionally, resize scanner's capacity for lines over 64K, see next example
  for scanner.Scan() {
    data := scanner.Text()
    testing.TestLog.Info("response line: %s", data)
    listener.OnMessage(data)
  }

  if err := scanner.Err(); err != nil {
    testing.TestLog.Error("failed reading response: %s", err)
  }

}
