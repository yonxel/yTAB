package me.neznamy.tab.shared.features.sorting.types;

import me.neznamy.tab.shared.TAB;
import me.neznamy.tab.shared.TabConstants;
import me.neznamy.tab.shared.features.sorting.Sorting;
import me.neznamy.tab.shared.platform.TabPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Sorting by groups with a fallback placeholder. Players are sorted by group
 * using configured order. If player's group is not found, output of the
 * placeholder is used to keep players of the same value together.
 */
public class GroupsOrPlaceholder extends SortingType {

    /** Map of sorted groups */
    private final LinkedHashMap<String, Integer> sortedGroups;

    /** Map of placeholder values to sorting index */
    private final Map<String, Integer> placeholderPositions = new LinkedHashMap<>();

    /** Next index for unknown placeholder values */
    private int nextIndex;

    /**
     * Constructs new instance with given parameters.
     *
     * @param   sorting
     *          Sorting feature
     * @param   options
     *          Options in format %placeholder%:group1,group2,...
     */
    public GroupsOrPlaceholder(Sorting sorting, String options) {
        super(sorting, "GROUPS_OR_PLACEHOLDER", extractPlaceholder(options));
        String[] split = options.split(":", 2);
        if (split.length == 2) {
            sortedGroups = convertSortingElements(split[1].split(","));
        } else {
            TAB.getInstance().getConfigHelper().startup().invalidSortingLine("GROUPS_OR_PLACEHOLDER:" + options,
                    "Expected format %placeholder%:group1,group2");
            sortedGroups = new LinkedHashMap<>();
        }
        nextIndex = sortedGroups.size() + 1;
    }

    private static String extractPlaceholder(String options) {
        int i = options.indexOf(":");
        return i == -1 ? options : options.substring(0, i);
    }

    @Override
    public String getChars(@NotNull TabPlayer p) {
        if (!valid) return "";
        String group = p.getGroup().toLowerCase();
        int position;
        if (sortedGroups.containsKey(group) && !group.equals(TabConstants.NO_GROUP)) {
            position = sortedGroups.get(group);
            p.sortingData.teamNameNote += "\n-> Primary group (&e" + p.getGroup() + "&r) is &a#" + position + "&r in sorting list.";
        } else {
            String value = setPlaceholders(p);
            String clean = value.trim();
            if (!sorting.getConfiguration().isCaseSensitiveSorting()) clean = clean.toLowerCase(Locale.US);
            if (!placeholderPositions.containsKey(clean)) {
                placeholderPositions.put(clean, nextIndex++);
            }
            position = placeholderPositions.get(clean);
            p.sortingData.teamNameNote += "\n-> " + sortingPlaceholder + " returned \"&e" + value + "&r\".";
        }
        return String.valueOf((char) (position + 47));
    }
}
