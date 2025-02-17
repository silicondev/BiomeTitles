package uk.phyre.biomeTitles.Helpers;

import java.util.LinkedList;
import java.util.List;

public final class ListHelper {
    public static List<String> GetStartsWith(List<String> list, String startWith) {
        var newList = new LinkedList<String>();
        for (String item : list) {
            if (item.trim().toLowerCase().startsWith(startWith.trim().toLowerCase()))
                newList.add(item);
        }
        return newList;
    }
}
