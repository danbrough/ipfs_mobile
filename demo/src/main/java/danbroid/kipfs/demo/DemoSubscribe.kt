package danbroid.kipfs.demo

import go.kipfs.pubsub.PubSub
import go.kipfs.pubsub.Pubsub

class DemoSubscribe : BaseDemo() {
  companion object {
    val topic = "beer"

    @JvmStatic
    fun main(args: Array<String>) {
      log.debug("running subscribe to topic: $topic")

      val app = DemoSubscribe()
      app.run()
    }
  }

  fun run() {
    log.debug("run()")
    val pubsub = PubSub()
    log.trace("created shell: $shell")

    log.trace("created pubsub")
    pubsub.test()
    Pubsub.subscribe(shell,topic){
      log.warn("received: $it")
    }
/*    log.trace("creating new subscription..")
    pubsub.newSubscription(shell, topic) {
      log.warn("received: $it")
    }*/

    log.trace("run() finished")
  }
}