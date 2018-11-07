package com.geek.pf.log.validator;

import java.util.regex.Pattern;

import org.codehaus.plexus.util.StringUtils;

import com.geek.pf.log.format.MessageFormat;

/**
 *
 *
 * @author jinkai.xu
 *
 * @date 2018/09/01
 */

public class MessageValidator {

    private static final String ID_PATTERN = "\\w*";

    private static final String FIELD_PATTERN = "\\w*";

    public static String validate(MessageFormat m) {

        String id = m.getId();

        String text = m.getText();

        if (StringUtils.isEmpty(id) || !Pattern.matches(ID_PATTERN, id)) {

            return "Error: ID validate fail.";
        }

        if (StringUtils.isEmpty(text)) {

            return "Error: Message text is null";
        }

        m.setText(text.trim());

        text = m.getText();

        if (!text.startsWith("description:")) {

            return "text:[ " + text + " ] validate fail. Error: Not found description.";
        }

        String[] props = text.split(",");

        for (String prop1 : props) {

            String prop = prop1.trim();

            String[] vs = prop.split(":");

            if (vs.length != 2) {

                return "text:[" + text + "] validate fail. Error text: " + prop;
            }

            String key = vs[0].trim();

            if (!Pattern.matches(FIELD_PATTERN, key)) {

                return "text:[ " + text + " ] validate fail. Error Key:[ " + key + " ]";
            }

        }

        return null;
    }

}
