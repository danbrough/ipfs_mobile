package pubsub

type SubscriptionListener interface {
  OnMessage(s string)
}

type PubSub struct {
}

type Subscription struct {
  Topic    string
  Listener SubscriptionListener
}

func (p *PubSub) NewSubscription(topic string, listener SubscriptionListener) *Subscription {
  return &Subscription{topic, listener}
}
