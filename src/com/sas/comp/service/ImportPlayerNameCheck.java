package com.sas.comp.service;

import com.sas.comp.models.*;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * this will not perform any importing but will instead
 * check all the players in the roster and note any new
 * players which would be added and print out similar
 * names. this is to help catch typos in player names and
 * catch cases where the name in the roster doesn't match
 * that stored in the database. think James vs Jim sort of
 * stuff. gives a chance to review all that before mistaken
 * player names are added
 */
public class ImportPlayerNameCheck {

    public static void main(final String[] args) throws Exception {
        final PlayerService playerService = new PlayerService();

        final File file = new File("./import/fall2025.json");
        final ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
        final Map root = mapper.readValue(file, Map.class);

        final List teams = (List) root.get("teams");
        
        TreeMap<String, TreeSet<String>> newplayers = new TreeMap<String, TreeSet<String>>();

        for(final Object object : teams) {
            final Map teamMap  = (Map) object;
            final List<String> playersList = (List<String>) teamMap.get("players");
            for(final String playerName : playersList) {
                Player player = playerService.find(playerName);
                if(player == null) {
                    System.out.println("Player " + playerName + " not found");
                    TreeSet<String> matches = new TreeSet<String>();
                    newplayers.put(playerName, matches);
                    List<Player> possible = playerService.fuzzyMatch(playerName);
                    for(int i=0 ; i < possible.size(); i++) {
                        String name = possible.get(i).getName();
                        matches.add(name);
                    }
                }
            }
            System.out.println();
            System.out.println("newplayers [possibleMatches]");
            Iterator<String> it = newplayers.keySet().iterator();
            while( it.hasNext() ) {
                String name = it.next();
                Set<String> matches = newplayers.get(name);
                System.out.println(name + " " + matches);
            }
            System.out.println();
        }
    }
}
