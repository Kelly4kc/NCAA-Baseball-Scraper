import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import play_by_play_objects.PlayByPlayGame;
import play_by_play_objects.PlayByPlayInning;
import play_by_play_objects.PlayByPlayLine;
import utils.Base;
import utils.FieldLocation;
import utils.NCAAUtils;
import utils.NameFixes;
import utils.Position;

public class NCAAEvents {
  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();
    int year = 2018;
    boolean isTeam = true;
    String teamName = "james_madison";
    String boxScoreFileName;
    String pbpFileName;
    String writeFileName;
    String writeFileName2;
    String writeFileName3;
    if (isTeam) {
      boxScoreFileName = "ncaa_" + year + "_D1/" + teamName + "_" + year + "_D1/" + teamName
          + "_box_scores/" + teamName + "_fielding_box_scores_" + year + "_D1.csv";
      pbpFileName = "ncaa_" + year + "_D1/" + teamName + "_" + year + "_D1/" + teamName
          + "_play_by_play/" + teamName + "_play_by_play_" + year + "_D1.csv";
      writeFileName = "ncaa_" + year + "_D1/" + teamName + "_" + year + "_D1/" + teamName
          + "_play_by_play/" + teamName + "_blank_play_by_play_" + year + "_D1.csv";
      writeFileName2 = "ncaa_" + year + "_D1/" + teamName + "_" + year + "_D1/" + teamName
          + "_play_by_play/" + teamName + "_split_blank_play_by_play_" + year + "_D1.csv";
      writeFileName3 = "ncaa_" + year + "_D1/" + teamName + "_" + year + "_D1/" + teamName
          + "_play_by_play/" + teamName + "_split_no_name_play_by_play_" + year + "_D1.csv";

    } else {
      boxScoreFileName =
          "ncaa_" + year + "_D1/ncaa_box_scores/ncaa_fielding_box_scores_" + year + "_D1.csv";;
      pbpFileName = "ncaa_" + year + "_D1/ncaa_play_by_play/ncaa_play_by_play_" + year + "_D1.csv";
      writeFileName =
          "ncaa_" + year + "_D1/ncaa_play_by_play/ncaa_blank_play_by_play_" + year + "_D1.csv";
      writeFileName2 = "ncaa_" + year + "_D1/ncaa_play_by_play/ncaa_split_blank_play_by_play_"
          + year + "_D1.csv";
      writeFileName3 = "ncaa_" + year + "_D1/ncaa_play_by_play/ncaa_split_no_name_play_by_play_"
          + year + "_D1.csv";
    }
    CSVWriter writer = NCAAUtils.CSVWriter(writeFileName, new String[] {"Event", "Frequency"});
    CSVWriter writer2 = NCAAUtils.CSVWriter(writeFileName2, new String[] {"Event", "Frequency"});
    CSVWriter writer3 = NCAAUtils.CSVWriter(writeFileName3, new String[] {"Event", "Frequency"});

    HashMap<String, Integer> noNameEvents = new HashMap<>();

    HashMap<Integer, HashMap<Integer, String>> gameRosters =
        getAllRostersFromBoxScoreFile(year, boxScoreFileName);

    HashMap<Integer, PlayByPlayGame> allGamePlayByPlay = getPlayByPlayFromFile(year, pbpFileName);

    Set<Integer> gameIds = allGamePlayByPlay.keySet();

    HashMap<String, Integer> events = new HashMap<>();

    int j = 0;
    for (Integer gameId : gameIds) {
      PlayByPlayGame game = allGamePlayByPlay.get(gameId);
      HashMap<Integer, String> gameRoster = gameRosters.get(gameId);
      HashMap<Integer, String[]> namePermutations = namePermutations(gameRoster);
      Set<Integer> playerIds = namePermutations.keySet();
      for (PlayByPlayInning inning : game.getInnings()) {
        for (PlayByPlayLine line : inning.getLines()) {
          String event = line.getEvent();

          if (event.endsWith(".")) {
            event = event.substring(0, event.length() - 1) + " ";
          }
          if (event.contains("(")) {
            event = event.substring(0, event.indexOf("(")) + "(count)"
                + event.substring(event.indexOf(")") + 1);
          }
          for (Integer playerId : playerIds) {
            for (int i = 0; i < namePermutations.get(playerId).length - 6; i++) {
              String name = namePermutations.get(playerId)[i];
              if (event.contains(name)) {
                event = event.replaceAll(name, "[name] ");
                break;
              }
            }
          }
          for (Integer playerId : playerIds) {
            for (int i = namePermutations.get(playerId).length - 6; i < namePermutations
                .get(playerId).length; i++) {
              String namePermutation = namePermutations.get(playerId)[i];
              if (event.contains(namePermutation)) {
                event = event.replaceAll(namePermutation, "[name] ");
                break;
              }
            }
          }
          if (event.contains("E")) {
            String temp = event.substring(0, event.indexOf("E") + 1);
            String temp2 = event.substring(event.indexOf("E") + 2);
            String number = event.substring(event.indexOf("E") + 1, event.indexOf("E") + 2);
            if (number.matches("\\d")) {
              temp += "[positionNumber]" + temp2;
              event = temp;
            }
          }
          if (event.contains("#")) {
            while (event.contains("#")) {
              String temp = event.substring(0, event.indexOf("#")) + "[number]";
              String numString = event.substring(event.indexOf("#") + 1);
              String number = numString.substring(0, numString.indexOf(" "));
              event = temp + numString.replace(number, "");
            }
          }

          for (FieldLocation fieldLocation : FieldLocation.values()) {
            String fieldLocationString = " " + fieldLocation.toString().toLowerCase();
            if (event.contains(fieldLocationString + " ")) {
              event = event.replaceAll(fieldLocationString + " ", " [fieldLocation] ");
            }
            if (event.contains(fieldLocationString + ",")) {
              event = event.replaceAll(fieldLocationString + ",", " [fieldLocation],");
            }
            if (event.contains(fieldLocationString + ":")) {
              event = event.replaceAll(fieldLocationString + ":", " [fieldLocation]:");
            }
            if (event.contains(fieldLocationString + ";")) {
              event = event.replaceAll(fieldLocationString + ";", " [fieldLocation];");
            }
            if (event.contains(fieldLocationString + "3a")) {
              event = event.replaceAll(fieldLocationString + "3a", " [fieldLocation]3a");
            }
            if (event.endsWith(fieldLocationString)) {
              event = event.substring(0, event.length() - fieldLocationString.length())
                  + " [fieldLocation]";
            }
          }
          for (Base base : Base.values()) {
            String baseString = " " + base.toString().toLowerCase();
            if (event.contains(baseString + " ")) {
              event = event.replaceAll(baseString + " ", " [base] ");
            }
            if (event.contains(baseString + ",")) {
              event = event.replaceAll(baseString + ",", " [base],");
            }
            if (event.contains(baseString + ":")) {
              event = event.replaceAll(baseString + ":", " [base]:");
            }
            if (event.contains(baseString + ";")) {
              event = event.replaceAll(baseString + ";", " [base];");
            }
            if (event.contains(baseString + "3a")) {
              event = event.replaceAll(baseString + "3a", " [base]3a");
            }
            if (event.endsWith(baseString)) {
              event = event.substring(0, event.length() - baseString.length()) + " [base]";
            }
          }
          for (Position position : Position.values()) {
            String positionString = " " + position.toString().toLowerCase();
            if (event.contains(positionString + " ")) {
              event = event.replaceAll(positionString + " ", " [position] ");
            }
            if (event.contains(positionString + ",")) {
              event = event.replaceAll(positionString + ",", " [position],");
            }
            if (event.contains(positionString + ":")) {
              event = event.replaceAll(positionString + ":", " [position]:");
            }
            if (event.contains(positionString + ";")) {
              event = event.replaceAll(positionString + ";", " [position];");
            }
            if (event.contains(positionString + "3a")) {
              event = event.replaceAll(positionString + "3a", " [position]3a");
            }
            if (event.endsWith(positionString)) {
              event = event.substring(0, event.length() - positionString.length()) + " [position]";
            }
          }
          if (noNameEvents.containsKey(event)) {
            noNameEvents.put(event, noNameEvents.get(event) + 1);
          } else {
            noNameEvents.put(event, 1);
          }
          if (events.containsKey(event)) {
            events.put(event, events.get(event) + 1);
          } else {
            events.put(event, 1);
          }
        }
      }

      j++;
      System.out.println(j + " / " + gameIds.size() + " Processed: " + gameId);
    }
    int eventNum = 0;
    int splitEventNum = 0;
    Set<String> eventSet = events.keySet();
    HashMap<String, Integer> splitEvents = new HashMap<>();
    HashMap<String, Integer> splitNoNameEvents = new HashMap<>();
    for (String event : eventSet) {
      writer.writeNext(new String[] {event, String.valueOf(events.get(event))});
      eventNum += events.get(event);
      for (String subEvent : splitEvents(event)) {
        if (subEvent.trim().contains("[name]")) {
          if (splitEvents.containsKey(subEvent.trim())) {
            splitEvents.put(subEvent.trim(), splitEvents.get(subEvent.trim()) + events.get(event));
          } else {
            splitEvents.put(subEvent.trim(), events.get(event));
          }
        } else {
          if (splitNoNameEvents.containsKey(subEvent.trim())) {
            splitNoNameEvents.put(subEvent.trim(),
                splitNoNameEvents.get(subEvent.trim()) + events.get(event));
          } else {
            splitNoNameEvents.put(subEvent.trim(), events.get(event));
          }
        }
      }
    }
    try {
      writer.flush();
    } catch (IOException e) {
      System.out.println("Failed to write to file");
    }

    Set<String> splitEventSet = splitEvents.keySet();
    for (String event : splitEventSet) {
      writer2.writeNext(new String[] {event, String.valueOf(splitEvents.get(event))});
      splitEventNum += splitEvents.get(event);
    }
    try {
      writer2.flush();
    } catch (IOException e) {
      System.out.println("Failed to write to file");
    }

    Set<String> noNameSplitSet = splitNoNameEvents.keySet();
    for (String event : noNameSplitSet) {
      writer3.writeNext(new String[] {event, String.valueOf(splitNoNameEvents.get(event))});
    }
    try {
      writer3.flush();
    } catch (IOException e) {
      System.out.println("Failed to write to file");
    }

    System.out.println("Total events: " + eventNum);
    System.out.println("Total split events: " + splitEventNum);
    long endTime = System.currentTimeMillis();
    System.out.println("Total Time: " + (endTime - startTime) / 1000.0);
  }

  private static HashMap<Integer, HashMap<Integer, String>> getAllRostersFromBoxScoreFile(int year,
      String fileName) {
    CSVReader reader = NCAAUtils.CSVReader(fileName);
    Iterator<String[]> it = reader.iterator();
    HashMap<Integer, HashMap<Integer, String>> gameRosters = new HashMap<>();
    it.next();
    HashMap<Integer, String> namesFound = new HashMap<>();
    while (it.hasNext()) {
      HashMap<Integer, String> gameRoster = new HashMap<>();
      String[] line = it.next();
      while (!line[3].equals("Totals")) {
        try {
          gameRoster.put(Integer.parseInt(line[2]), line[3]);
        } catch (NumberFormatException n) {
          gameRoster.put(-1, line[3]);
        }
        line = it.next();
      }
      line = it.next();
      while (!line[3].equals("Totals")) {
        try {
          gameRoster.put(Integer.parseInt(line[2]), line[3]);
        } catch (NumberFormatException n) {
          gameRoster.put(-1, line[3]);
        }
        line = it.next();
      }
      gameRosters.put(Integer.parseInt(line[0]), gameRoster);
    }
    Set<Integer> ids = namesFound.keySet();
    for (Integer id : ids) {
      System.out.println("names.put(" + id + ", \"" + namesFound.get(id) + "\");");
    }
    return gameRosters;
  }

  private static String[] splitEvents(String event) {
    String[] events = null;
    if (event.matches(".*\\d p\\.m\\..*") || event.matches(".*\\d a\\.m\\..*")
        || event.matches(".*\\d pm.*") || event.matches(".*\\d am.*")
        || event.matches(".*\\d p.m.*") || event.matches(".*\\d a.m.*")
        || event.matches(".*\\d P\\.M\\..*") || event.matches(".*\\d A\\.M\\..*")
        || event.matches(".*\\d PM.*") || event.matches(".*\\d AM.*")
        || event.matches(".*\\d P\\.M.*") || event.matches(".*\\d A\\.M.*")
        || event.contains("Count") || event.contains("delay") || event.contains("Delay")
        || event.contains("First Pitch")) {
      events = new String[] {event};
    } else {
      if (event.contains(":") || event.contains(";") || event.contains("3a")) {
        if (event.contains(":")) {
          events = event.split(":");
        }
        if (event.contains(";")) {
          events = event.split(";");
        }
        if (event.contains("3a")) {
          events = event.split("3a");
        }
      } else {
        events = new String[] {event};
      }
    }
    return events;
  }

  public static HashMap<Integer, PlayByPlayGame> getPlayByPlayFromFile(int year, String fileName) {
    HashMap<Integer, PlayByPlayGame> games = new HashMap<>();
    CSVReader reader = NCAAUtils.CSVReader(fileName);
    Iterator<String[]> it = reader.iterator();
    it.next();
    String[] line = it.next();
    while (it.hasNext()) {
      PlayByPlayGame game = new PlayByPlayGame(year, Integer.parseInt(line[0]),
          Integer.parseInt(line[1]), line[2], line[3]);
      int inning = 1;
      while (line != null && inning <= Integer.parseInt(line[1])) {
        inning = Integer.parseInt(line[1]);
        PlayByPlayInning inningPlayByPlay = new PlayByPlayInning(year, Integer.parseInt(line[0]),
            Integer.parseInt(line[1]), line[2], line[3]);
        while (!line[4].equals("Inning Summary")) {
          inningPlayByPlay.addLine(new PlayByPlayLine(year, line));
          line = it.next();
        }
        inningPlayByPlay.setAwaySummary(line[5]);
        inningPlayByPlay.setHomeSummary(line[6]);
        inningPlayByPlay.setScore(line[7]);
        game.addInning(inningPlayByPlay);
        line = it.next();
      }
      games.put(game.getGameId(), game);
    }
    return games;
  }


  private static HashMap<Integer, String[]> namePermutations(HashMap<Integer, String> roster) {
    HashMap<Integer, String[]> allNames = new HashMap<>();
    Set<Integer> ids = roster.keySet();
    for (Integer id : ids) {
      String name = NameFixes.fixNames(id);
      if (name == null) {
        name = roster.get(id);
      }
      String lastName = null;
      String firstName = null;
      try {
        lastName = name.substring(0, name.indexOf(','));
        firstName = name.substring(name.indexOf(',') + 2);
      } catch (StringIndexOutOfBoundsException s) {
        try {
          lastName = name.substring(0, name.indexOf(' '));
          firstName = name.substring(name.indexOf(' ') + 2);
        } catch (StringIndexOutOfBoundsException s1) {
          lastName = name;
          firstName = "NULL";
        }
      }

      String[] names = new String[76];
      names[0] = name + " ";
      names[1] = lastName + ", " + firstName.substring(0, 1) + ". ";
      names[2] = lastName + "," + firstName.substring(0, 1) + ". ";
      names[3] = lastName + ", " + firstName.substring(0, 1) + " ";
      names[4] = lastName + "," + firstName.substring(0, 1) + " ";
      names[5] = lastName + " " + firstName.substring(0, 1) + " ";
      names[6] = lastName + "," + firstName + " ";
      names[7] = firstName.substring(0, 1) + " " + lastName + " ";
      names[8] = firstName.substring(0, 1) + ". " + lastName + " ";
      names[9] = firstName.substring(0, 1) + "." + lastName + " ";
      names[10] = firstName + " " + lastName + " ";
      names[11] = firstName + " " + lastName.toUpperCase() + " ";
      names[12] = lastName.toUpperCase() + ", " + firstName + " ";
      names[13] = lastName.toUpperCase() + "," + firstName + " ";
      if (firstName.length() > 1) {
        names[14] = firstName.substring(0, 2) + ". " + lastName + " ";
        names[15] = firstName.substring(0, 2) + ". " + lastName.toUpperCase() + " ";
        names[16] = lastName + ", " + firstName.substring(0, 2) + ". ";
        names[17] = lastName + "," + firstName.substring(0, 2) + ". ";
        names[18] = lastName.toUpperCase() + ", " + firstName.substring(0, 2) + ". ";
        names[19] = lastName.toUpperCase() + "," + firstName.substring(0, 2) + ". ";
        names[20] = lastName + ", " + firstName.substring(0, 2) + " ";
        names[21] = lastName + "," + firstName.substring(0, 2) + " ";
        names[22] = lastName.toUpperCase() + ", " + firstName.substring(0, 2) + " ";
        names[23] = lastName.toUpperCase() + "," + firstName.substring(0, 2) + " ";
      } else {
        names[14] = firstName + ". " + lastName + " ";
        names[15] = firstName + ". " + lastName.toUpperCase() + " ";
        names[16] = lastName + ", " + firstName + ". ";
        names[17] = lastName + "," + firstName + ". ";
        names[18] = lastName.toUpperCase() + ", " + firstName + ". ";
        names[19] = lastName.toUpperCase() + "," + firstName + ". ";
        names[20] = lastName + ", " + firstName + " ";
        names[21] = lastName + "," + firstName + " ";
        names[22] = lastName.toUpperCase() + ", " + firstName + " ";
        names[23] = lastName.toUpperCase() + "," + firstName + " ";
      }
      names[24] = names[0].toUpperCase();
      names[25] = names[1].toUpperCase();
      names[26] = names[2].toUpperCase();
      names[27] = names[3].toUpperCase();
      names[28] = names[4].toUpperCase();
      names[29] = names[5].toUpperCase();
      names[30] = names[6].toUpperCase();
      names[31] = names[7].toUpperCase();
      names[32] = names[8].toUpperCase();
      names[33] = names[9].toUpperCase();
      names[34] = names[10].toUpperCase();
      names[35] = names[11].toUpperCase();
      names[36] = names[12].toUpperCase();
      names[37] = names[13].toUpperCase();
      names[38] = names[14].toUpperCase();
      names[39] = names[15].toUpperCase();
      names[40] = names[16].toUpperCase();
      names[41] = names[17].toUpperCase();
      names[42] = names[18].toUpperCase();
      names[43] = names[19].toUpperCase();
      names[44] = names[20].toUpperCase();
      names[45] = names[21].toUpperCase();
      names[46] = names[22].toUpperCase();
      names[46] = names[23].toUpperCase();


      names[47] = names[24];
      names[48] = names[25];
      names[49] = names[26];
      names[50] = names[27];
      names[51] = names[28];
      names[52] = names[29];
      names[53] = names[30];
      names[54] = names[31];
      names[55] = names[32];
      names[56] = names[33];
      names[57] = names[34];
      names[58] = names[35];
      names[59] = names[36];
      names[60] = names[37];
      names[61] = names[38];
      names[62] = names[39];
      names[63] = names[40];
      names[64] = names[41];
      names[65] = names[42];
      names[66] = names[43];
      names[67] = names[44];
      names[68] = names[45];
      names[69] = names[46];

      names[names.length - 6] = lastName + ", ";
      names[names.length - 5] = lastName + ", ";
      names[names.length - 4] = names[names.length - 5].toUpperCase();
      names[names.length - 3] = lastName + " ";
      names[names.length - 2] = lastName + " ";
      names[names.length - 1] = names[names.length - 3].toUpperCase();

      if (lastName.length() > 2 && lastName.charAt(2) >= 'A' && lastName.charAt(2) <= 'Z') {
        for (int i = 0; i < 41; i++) {
          if (names[i].contains(lastName.substring(0, 2).toUpperCase())) {
            names[i] = names[i].replaceFirst(lastName.substring(0, 2).toUpperCase(),
                lastName.substring(0, 1).toUpperCase() + lastName.substring(1, 2).toLowerCase());
          }
        }
        names[names.length - 5] = names[names.length - 5].toUpperCase();
        names[names.length - 4] =
            names[names.length - 4].replaceFirst(lastName.substring(0, 2).toUpperCase(),
                lastName.substring(0, 1).toUpperCase() + lastName.substring(1, 2).toLowerCase());
        names[names.length - 2] = names[names.length - 2].toUpperCase();
        names[names.length - 1] =
            names[names.length - 1].replaceFirst(lastName.substring(0, 2).toUpperCase(),
                lastName.substring(0, 1).toUpperCase() + lastName.substring(1, 2).toLowerCase());
      }

      allNames.put(id, names);
    }
    return allNames;
  }
}
