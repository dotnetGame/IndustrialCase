package com.iteale.industrialcase.core.network;


import net.minecraft.server.level.ServerPlayer;

import java.util.*;

public class TeUpdateDataServer
{
    void addGlobalField(String name) {
        if (!this.globalFields.add(name))
            return;
        if (!this.playerFieldMap.isEmpty()) {
            for (Set<String> playerFields : this.playerFieldMap.values()) {
                playerFields.remove(name);
            }
        }
    }

    void addPlayerField(String name, ServerPlayer player) {
        if (this.globalFields.contains(name))
            return;
        Set<String> playerFields = this.playerFieldMap.get(player);

        if (playerFields == null) {
            playerFields = new HashSet<>();
            this.playerFieldMap.put(player, playerFields);
        }

        playerFields.add(name);
    }

    Collection<String> getGlobalFields() {
        return this.globalFields;
    }

    Collection<String> getPlayerFields(ServerPlayer player) {
        Set<String> ret = this.playerFieldMap.get(player);

        if (ret == null) {
            return Collections.emptyList();
        }
        return ret;
    }


    private final Set<String> globalFields = new HashSet<>();
    private final Map<ServerPlayer, Set<String>> playerFieldMap = new IdentityHashMap<>();
}

