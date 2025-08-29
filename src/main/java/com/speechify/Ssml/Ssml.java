package com.speechify.Ssml;

import java.util.*;

/**
 * SSML (Speech Synthesis Markup Language) is a subset of XML specifically
 * designed for controlling synthesis. You can see examples of how the SSML
 * should be parsed in com.speechify.SSMLTest in `src/test/java/SSMLTest.java`.
 *
 * You may:
 *  - Read online guides to supplement information given in com.speechify.SSMLTest to understand SSML syntax.
 * You must not:
 *  - Use XML parsing libraries or the DocumentBuilderFactory. The task should be solved only using string manipulation.
 *  - Read guides about how to code an XML or SSML parser.
 */
public class Ssml {

    // Parses SSML to a SSMLNode, throwing on invalid SSML
    public static SSMLNode parseSSML(String ssml) {
        ssml=ssml.trim();
        // NOTE: Don't forget to run unescapeXMLChars on the SSMLText
        throw new UnsupportedOperationException("Implement this function");
        return parseElements(ssml);
    }
    private static SSMLNode parseElements(String input) {
        if(!input.startsWith("<")) {
            return new SSMLText(unescapeXMLChars(input));
        }
        int spaceIdx=input.indexOf(" ");
    int endIdx=input.indexOf(">");
    boolean selfClosing=input.endsWith("/>");
    String name;
    if(spaceIdx!=-1 && spaceIdx < endIdx)
    {
        name=input.substring(1,spaceIdx);
    }
    else{
        name=input.substring(1,endIdx);
    }
    List<SSMLAttribute> attributes=new ArrayList<>();
    if(spaceIdx!=-1 && spaceIdx < endIdx)
    {
        String attrPart = input.substring(spaceIdx + 1, endIdx).trim();
        if(!attrPart.isEmpty())
        {
            String[] parts = attrPart.split("=");
            for (int i=0; i < parts.length - 1; i++) {
                String attrName=parts[i].trim();
                String attrValue=parts[i+1].trim();
                if (attrValue.startsWith("\"")) {
                    int quoteEnd = attrValue.indexOf("\"", 1);
                    String value= attrValue.substring(1, quoteEnd);
                    attributes.add(new SSMLAttribute(attrName, value));
                    parts[i+1]=attrValue.substring(quoteEnd+1).trim();
                }
            }
        }
    }
    if(selfClosing)
    { 
        return new SSMLElement(name,attributes,new ArrayList<>());

    }
    String closingTag="</"+name+">";
    int closingIdx=input.lastIndexOf(closingTag);
    if(closingIdx==-1)
    {
        throw new UnsupportedOperationException("Implement this function");
    }
    String inner=input.substring(endIdx+1,closingIdx);
    List<SSMLNode> children=new ArrayList<>();
    while(!inner.isEmpty())
    {
        inner=inner.trim();
        if(inner.startsWith("<"))
        {
            int nextClose=inner.indexOf(">");
            if(nextClose==-1) break;
            int nextTagEnd=findClosingTag(inner);
            childern.add(parseElements(inner.substring(0,nextTagEnd)));
            inner=inner.substring(nextTagEnd).trim();
        }
        else
        {
            int nextTag= inner.indexOf("<");
            if(nextTag==-1)
            {
                children.add(new SSMLText(unescapeXMLChars(inner)));
                inner="";
            }
            else
            {
                children.add(new SSMLText(unescapeXMLChars(inner.substring(0,nextTag))));
                inner=inner.substring(nextTag).trim();
            }

        }
        
    
}
return new SSMLElement(name,attributes,children);
    }

    private static int findClosingTag(String input)
    {
        int depth=0;
        for(int i=0;i<input.length();i++)
        {
            if(input.charAt(i)=='<' && i+1 <input.length() && input.charAt(i+1)!='/')
            {
                depth++;
            }
            else if(input.startsWith("</",i))
            {
                depth--;
                if(depth==0)
                {
                    int closeIdx=input.indexOf(">",i);
                    if(closeIdx==-1)
                    {
                        throw new UnsupportedOperationException("Implement this function");
                    }
                    return closeIdx+1;
                }
            }
        }
        return input.length();
        
}
    

    // Recursively converts SSML node to string and unescapes XML chars
    public static String ssmlNodeToText(SSMLNode node) {
        throw new UnsupportedOperationException("Implement this function");
    }

    // Already done for you
    public static String unescapeXMLChars(String text) {
        return text.replace("&lt;", "<").replace("&gt;", ">").replace("&amp;", "&");
    }

    public sealed interface SSMLNode permits SSMLElement, SSMLText {}

    public record SSMLElement(String name, List<SSMLAttribute> attributes, List<SSMLNode> children) implements SSMLNode {}

    public record SSMLAttribute(String name, String value) {}

    public record SSMLText(String text) implements SSMLNode {}
}
