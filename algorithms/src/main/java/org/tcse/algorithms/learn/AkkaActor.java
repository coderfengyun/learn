package org.tcse.algorithms.learn;

import java.io.Serializable;

import org.junit.Test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class AkkaActor {

	public static class Greeting implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public final String who;

		public Greeting(String who) {
			this.who = who;
		}
	}

	public static class GreetingActor extends UntypedActor {

		@Override
		public void onReceive(Object message) throws Exception {
			if (message instanceof Greeting) {
				System.out.println(((Greeting) message).who);
			}
		}
	}

	@Test
	public void test() {
		ActorSystem system = ActorSystem.create("MyGreetingSystem");
		ActorRef greeter = system.actorOf(Props.create(GreetingActor.class),
				"greeter");
		greeter.tell(new Greeting("hello akka"), ActorRef.noSender());
	}
}
