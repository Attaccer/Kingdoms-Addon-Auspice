package top.mckingdom.auspice.utils;

import org.kingdoms.locale.SupportedLanguage;
import top.mckingdom.auspice.AuspiceAddon;
import top.mckingdom.auspice.land.land_categories.StandardLandCategory;

import java.util.ArrayList;
import java.util.List;

public class LandCategoriesUtil {

    public static List<String> getLandCategories(String starts, SupportedLanguage language) {

        List<String> out = new ArrayList<>();
        AuspiceAddon.get().getLandCategoryRegistry().getRegistry().values().forEach( category -> {
            String s = category.getName(language);
            if (s.startsWith(starts)) {
                out.add(s);
            }
        });
        return out;

    }



}
