package com.gmail.jackdonofrio99.chat;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathMessageListener implements Listener {

	// Custom messages - TODO - move to yml file
	// Heavily inspired by Terraria death messages
	// also note - this will heavily be expanded upon

	private final String[] FALL = { "discovered gravity.", "didn't bounce.", "left a small crater.", "faceplanted.",
			"believed they could fly." };

	private final String[] DROWN = { "forgot to breathe", "is sleeping with the fishes.",
			"attempted baptism by immersion.", "forgot to swim." };

	private final String[] LAVA = { "tried to swim in lava.", "was incinerated.", "got melted.",
			"died in a pool of lava as a virgin sacrifice to Baal." };

	private final String[] BURN = { "died as a burnt offering to Herobrine.", "was burned as a virgin sacrifice.",
			"was incinerated.", "couldn't put the fire out." };

	private final String[] LIGHTNING = { "was smote by God.", "was smote by Zeus." };

//	private final String[] EXPLOSION = { "blew up." };

	private final String[] GENERAL = { "was impaled.", "was torn in half", "had their entrails ripped out.",
			"was destroyed.", "got rekt.", "watched their innards become outards.",
			"had their plea for death finally answered.", "was eviscerated.", "was removed from the mortal realm.",
			"has passed into the afterlife.", "became a virgin sacrifice." };

	private boolean diedByFall(String msg) {
		return msg.contains("fell") || msg.contains("hit the ground");
	}

	private boolean diedByDrown(String msg) {
		return msg.contains("drowned");
	}

	private boolean diedByLava(String msg) {
		return msg.contains("tried to swim in lava");
	}

	private boolean diedByBurn(String msg) {
		return msg.contains("flames") || msg.contains("burn") || msg.contains("walked into fire");
	}

	private boolean diedByLightning(String msg) {
		return msg.contains("struck by lightning");
	}

//	private boolean diedByExplosion(Sting msg) {
//		return msg.contains("blew up") || msg.contains("blown up");
//	}
//
//	private boolean diedByElytra(String msg) {
//		return msg.contains("kinetic");
//	}r

	/**
	 * Method for setting custom death messages.
	 * 
	 * @param event
	 */
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		// randomly pick message
		String message = determineMessage(e.getDeathMessage());
		e.setDeathMessage(ChatColor.AQUA + "" + e.getEntity().getDisplayName() + ChatColor.BLUE + " " + message);
	}

	/**
	 * Determine the category of message based on the default death message, then
	 * return a random message from that category
	 * 
	 * @param defaultMessage
	 * @return
	 */
	private String determineMessage(String defaultMessage) {
		String[] deathType = GENERAL;
		if (diedByFall(defaultMessage)) {
			deathType = FALL;
		} else if (diedByDrown(defaultMessage)) {
			deathType = DROWN;
		} else if (diedByLava(defaultMessage)) {
			deathType = LAVA;
		} else if (diedByBurn(defaultMessage)) {
			deathType = BURN;
		} else if (diedByLightning(defaultMessage)) {
			deathType = LIGHTNING;
		}
		return pickRandomMessage(deathType);
	}

	/**
	 * Return a random message from a list.
	 * 
	 * @param messages
	 * @return
	 */
	private String pickRandomMessage(final String[] messages) {
		return messages[(int) (Math.random() * messages.length)];
	}

}
