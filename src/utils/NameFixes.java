package utils;

import java.util.HashMap;

public class NameFixes {

  private static HashMap<Integer, String> names;
  static {
    names = new HashMap<>();
    names.put(1978909, "Giachin, Ant");
    names.put(1529107, "Collier, Dan");
    names.put(1770525, "Gardner, Dan");
    names.put(1880603, "Herrera, Dan");
    names.put(1994777, "Ducote, Cody");
    names.put(1758482, "Wms-Sutton, Dwanya");
    names.put(1866000, "Free, James");
    names.put(1644309, "Hancock, JJ");
    names.put(1997841, "Lagreco, JP");
    names.put(1978894, "Siriani, Bre");
    names.put(1768974, "Gaddis, Nicho");
    names.put(1995786, "Zakosek, Dan");
    names.put(1888009, "LeBlanc, Colby");
    names.put(1769741, "Hudgins, Mat");
    names.put(1888523, "Suarez, Chris");
    names.put(1864203, "DeBlaise, Dominic");
    names.put(1998345, "Sanchez, Enrique");
    names.put(1661696, "Assante, Ant");
    names.put(1997573, "Piccolino, Anthony");
    names.put(1873666, "Wthrspoon, Nate");
    names.put(1880834, "Dicochea, Jason");
    names.put(1756466, "Miles, Jacky");
    names.put(1879604, "HayesSaltare, Nicholas");
    names.put(1756465, "McDaniel, Wil");
    names.put(1770544, "Kuchta, Nate");
    names.put(1656873, "Daniel, Clayt");
    names.put(1879341, "Fugitt, Addi");
    names.put(1754669, "Williams, RJ");
    names.put(1645600, "Briggs, Aust");
    names.put(1767256, "Rensel jr, John");
    names.put(1881950, "Haddad, Ram");
    names.put(1754207, "Stunkel jr., Duke");
    names.put(1877594, "Esparza, Serg");
    names.put(1751379, "Alexander, Cory");
    names.put(1866831, "Lockwood-Pow, Griffin");
    names.put(1977674, "Libunao, Raff");
    names.put(1879877, "Walters, Bla");
    names.put(1873478, "Mclean, Zach");
    names.put(1996099, "Nettervil, Steele");
    names.put(1977470, "Ouellette, Kyle");
    names.put(1752434, "Duhon, Dusti");
    names.put(1661552, "LeBlanc, Horace");
    names.put(1863281, "Bynum, Scooter");
    names.put(1656694, "Cole, Dav");
    names.put(1997422, "Black, T.J");
    names.put(1768043, "Oloruntimi, Olajide");
    names.put(1997164, "Chandel, Adi");
    names.put(1996394, "Strachan, Ale");
    names.put(1877607, "Soriano, JP");
    names.put(1981539, "Vaughn, Gregory");
    names.put(1650075, "Hunt, AJ");
    names.put(1542037, "Dalporto, Tim");
    names.put(1774237, "Arias, Luis");
    names.put(1984660, "Cordova-Smit, Peyton");
    names.put(1998996, "Lacoste, Peyton");
    names.put(1880977, "Schwertfeger, Russel");
    names.put(1996944, "Sanchez, Ric");
    names.put(1977745, "Schwartzenbe, Zach");
    names.put(1996943, "Softley, Aust");
    names.put(1769345, "McConnel, Matt");
    names.put(1769348, "Townsend-Cha, Jimmy");
    names.put(1881219, "Depreta-John, Tyler");
    names.put(1997503, "Maxwell, Car");
    names.put(1871293, "Moyer, Octavi");
    names.put(1868222, "BattenfieldP, Peyton");
    names.put(1774777, "Ridgely, Cole");
    names.put(1871289, "Oliver, Dalla");
    names.put(1862841, "Cocciadiferr, Matthew");
    names.put(1997243, "Varela, Roger");
    names.put(1997499, "Fitzpatrck, Jordan");
    names.put(1973430, "Meola, Jon.");
    names.put(1755315, "Bengtson, Chandler");
    names.put(1998514, "Smith II, Jonathan");
    names.put(1754538, "Mcgee, Mike");
    names.put(1973420, "Sullivan IV, Billy");
    names.put(1998506, "Parker, Jarf");
    names.put(1754541, "Zilinsky, Myles");
    names.put(1981601, "Curtis, Jorda");
    names.put(1771739, "Maisonet, Darnell");
    names.put(1774297, "Spencer, II, Fredrick");
    names.put(1871066, "Crockett, Ryan");
    names.put(1765075, "Bowerbank, Luke");
    names.put(1998039, "Smith, Antho");
    names.put(1868245, "Achecar III, Freddy");
    names.put(1997524, "Strahan, Bro");
    names.put(1774294, "Parrish, Marshall");
    names.put(1891019, "Szczasny, Tommy");
    names.put(1770948, "Dinicola, Harrison");
    names.put(1750009, "O'Connor, Kevin");
    names.put(1995768, "Merritt, Bra");
    names.put(1880561, "Watari, Just");
    names.put(1995763, "Emond, Malac");
    names.put(1529063, "France, JP");
    names.put(1881580, "Greckel, Nola");
    names.put(1881581, "Simpson, Tren");
    names.put(1995757, "Sikes, Phill");
    names.put(1974765, "Ulick, Austi");
    names.put(1974762, "Vasic, Niko");
    names.put(1863657, "Shedler, Tyler");
    names.put(1526753, "Frankenreide, Andrew");
    names.put(1753068, "Anastasia, Michael");
    names.put(1974761, "Hudson, Brad");
    names.put(1881574, "Naismith, And");
    names.put(1882336, "LaCascio, Noah");
    names.put(1996769, "Varela, Jordan");
    names.put(1881571, "Robinson, Cha");
    names.put(1872296, "Ramirez, III");
    names.put(1863289, "Thomas, Nate");
  }

  public static String fixNames(Integer id) {
    if (names.containsKey(id)) {
      return names.get(id);
    }
    return null;
  }
}