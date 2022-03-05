package me.daslastic.fool.util;

import org.apache.commons.lang.StringUtils;

public class UHelp {

    public static final String TREE_COLOR = "&7";
    public static final String BUILDER = " ";
    public static final String TREE = StringUtils.repeat(BUILDER, 50);

    public static String getTree(String title, String innerText) {
        int startI = TREE.length()/2 - title.length()/2 - 3 - 1;

        StringBuilder treeTitle = new StringBuilder();
        for(int i = 0; i < TREE.length() - 6; i++) {
            if(i >= startI - 1 && i <= startI + title.length()) {
                if(i == startI - 1) {
                    treeTitle.append(BUILDER);
                    treeTitle.append("&f&7&m  &f&7[ &f&a&l");
                } else if(i == startI + title.length()) {
                    treeTitle.append("&f&7 ]&m  &f");
                    treeTitle.append(BUILDER);
                } else {
                    treeTitle.append(title.charAt(i - startI));
                }
                continue;
            }
            treeTitle.append(BUILDER);
        }

        if(innerText.isBlank()) {
            innerText = "               Not yet implemented...               ";
        }

        return UText.color(TREE_COLOR + treeTitle + "&f\n" + innerText + "&f");
    }

}
