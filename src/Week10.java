import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week10 {
    /** Get all function. */
    public static List<String> getAllFunctions(String fileContent) {
        List<String> result = new ArrayList<>();
        int index = 0;
        String[] lineSegments = fileContent.split("\n");

        String packageName = "";
        if (lineSegments[0].contains("package")) {
            String[] parts = lineSegments[0].split(" ");
            packageName = parts[1].replaceAll(";", "");
            ++index;
        }

        List<String> libraries = new ArrayList<>();

        libraries.add("java.lang.Double");
        libraries.add("java.lang.Integer");
        libraries.add("java.lang.Character");
        libraries.add("java.lang.Float");
        libraries.add("java.io.InputStream");
        libraries.add("java.util.ArrayList");
        libraries.add("java.util.List");
        libraries.add("java.lang.String");
        libraries.add("java.lang.Object");
        libraries.add("java.lang.Class");
        libraries.add("java.lang.Iterable");
        libraries.add("java.util.Date");
        libraries.add("java.util.Objects");

        int lastLibrary = index;

        for (int i = index; i < lineSegments.length; ++i) {
            int comment = 0;
            while (i < lineSegments.length && lineSegments[i].trim().startsWith("//")) {
                i++;
            }
            if (i >= lineSegments.length) {
                break;
            }
            do {
                if (lineSegments[i].trim().startsWith("/*")
                        && !lineSegments[i].trim().endsWith("*/")) {
                    comment++;
                } else if (lineSegments[i].trim().endsWith("*/")) {
                    comment--;
                }
                i++;
            } while (i < lineSegments.length && comment > 0);
            i--;
            if (lineSegments[index].trim().startsWith("/*")
                    && lineSegments[index].trim().endsWith("*/")) {
                index++;
            }

            String className = "";
            List<String> elements = Arrays.asList(lineSegments[i].split(" "));
            if (elements.contains("class") || elements.contains("interface")
                    && lineSegments[i].contains("{")) {

                if (elements.contains("class")) {
                    className = elements.get(elements.indexOf("class") + 1);
                }
                if (elements.contains("interface")) {
                    className = elements.get(elements.indexOf("interface") + 1);
                }
                if (className.contains("<")) {
                    className = className.substring(0, className.indexOf("<"));
                }
                if (!packageName.isEmpty()) {
                    libraries.add(packageName + "." + className);
                } else {
                    libraries.add(className);
                }

                className = "";
                continue;
            } else if (lineSegments[i].contains("import")) {
                String[] parts = lineSegments[i].split(" ");
                libraries.add(parts[parts.length - 1].replaceAll(";", ""));
                ++lastLibrary;
            }
        }

        index = lastLibrary;

        String className = "";

        for (; index < lineSegments.length; index++) {
            int comment = 0;
            while (index < lineSegments.length
                    && lineSegments[index].trim().startsWith("//")) {
                index++;
            }
            if (index >= lineSegments.length) {
                break;
            }
            do {
                if (lineSegments[index].trim().startsWith("/*")
                        && !lineSegments[index].trim().endsWith("*/")) {
                    comment++;
                } else if (lineSegments[index].trim().endsWith("*/")) {
                    comment--;
                }
                index++;
            } while (index < lineSegments.length && comment > 0);
            index--;
            if (lineSegments[index].trim().startsWith("/*")
                    && lineSegments[index].trim().endsWith("*/")) {
                index++;
            }

            List<String> elements
                    = Arrays.asList(lineSegments[index].split(" "));

            if (elements.contains("static")
                    && lineSegments[index].contains("(")) {
                if (lineSegments[index].contains(")")
                        && !lineSegments[index].contains("{")) {
                    continue;
                }

                int openParentheses = lineSegments[index].indexOf("(");
                int closingParentheses;
                String functionName = "";
                for (int i = 0; i < elements.size(); i++) {
                    if (elements.get(i).contains("(")) {
                        int pos = elements.get(i).indexOf("(");
                        functionName = elements.get(i).substring(0, pos);
                        break;
                    }
                }

                String parameterList = "";
                if (lineSegments[index].contains(")") && lineSegments[index].contains("{")) {
                    closingParentheses = lineSegments[index].indexOf(")");
                    parameterList
                            = getParamsType(lineSegments[index].substring(openParentheses
                            + 1, closingParentheses), libraries);
                } else {
                    StringBuilder pramTmp
                            = new StringBuilder(lineSegments[index].substring(openParentheses + 1));
                    index++;
                    while (!lineSegments[index].contains(")")) {
                        StringBuilder line = new StringBuilder(lineSegments[index].trim());
                        pramTmp.append(line);
                        ++index;
                    }

                    StringBuilder line = new StringBuilder(lineSegments[index].trim());

                    if (!line.toString().contains("{")) {
                        continue;
                    }
                    closingParentheses = line.indexOf(")");
                    pramTmp.append(line.substring(0, closingParentheses));

                    parameterList = getParamsType(pramTmp.toString(), libraries);
                }
                result.add(functionName + "(" + parameterList + ")");
            }
        }
        return result;
    }

    /** full name. */
    public static String fullClassName(String className, List<String> libraries) {
        String simpleName = "";
        int posOpen = className.indexOf("<");
        int posClose = className.indexOf(">");
        String tmp = "";
        if (posOpen != -1) {
            simpleName = className.substring(0, posOpen);
            tmp = getParamsType(className.substring(posOpen + 1, posClose), libraries);
            tmp = "<" + tmp + ">";
        }
        for (int i = 0; i < libraries.size(); i++) {

            if (className.contains("<") && libraries.get(i).contains(simpleName)
                    || libraries.get(i).endsWith(className)) {
                return libraries.get(i) + tmp;
            }
        }
        return null;
    }

    /** get params type. */
    public static String getParamsType(String parameterList, List<String> libraries) {
        String[] argumentList = parameterList.split(", ");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < argumentList.length; i++) {
            if (i != 0) {
                result.append(",");
            }
            String[] parts = argumentList[i].split(" ");
            if (!parts[0].isEmpty()) {
                parts[0] = normalize(parts[0]);
                String tmp = fullClassName(parts[0], libraries);
                if (tmp == null) {
                    result.append(parts[0]);
                } else {
                    result.append(tmp);
                }
            }
        }
        return result.toString();
    }

    /** Normalize. */
    public static String normalize(String typeName) {
        String result = typeName.replaceAll("\\.\\.\\.", "");
        return result;
    }
}
