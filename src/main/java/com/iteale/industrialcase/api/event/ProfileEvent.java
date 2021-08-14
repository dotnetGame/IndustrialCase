package com.iteale.industrialcase.api.event;

import net.minecraftforge.eventbus.api.Event;

import java.util.Set;


/** Events related to profiles within IC2 */
public abstract class ProfileEvent extends Event {
	/** Called once profiles have been loaded from files but before the user's selected one is switched to */
	public static class Load extends ProfileEvent {
		public Load(Set<String> loaded, String active) {
			this.loaded = loaded;
			this.active = active;
		}

		/** The names of all found profiles */
		public final Set<String> loaded;
		/** The name of the profile the user has selected, that will be attempted to be used */
		public final String active;
	}

	/** Called when a profile switch occurs */
	public static class Switch extends ProfileEvent {
		public Switch(String from, String to) {
			this.from = from;
			this.to = to;
		}

		public final String from, to;
	}
}