/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author vetal
 */
public class ParseBookSax extends DefaultHandler {

    //строковый буфер для накопления символов 
    //текстовых элементов документа
    private StringBuffer stringBuffer;

    public static void main(String[] args) {
        String fileName = "books.xml";

        //создаем экземпляр обработчика SAX
        DefaultHandler defaultHandler = new ParseBookSax();
        //создаем экземпляр класса SAXParserFactory
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try{
            SAXParser parser = factory.newSAXParser();
            parser.parse(new File(fileName), defaultHandler);
        }catch(Exception e){
            e.printStackTrace();
        }      
    }
    
    /**
    *обрабатывает событие начала документа
    */
    public void strartDocument() throws SAXException{
        System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    }
    
    /**
    *обрабатывает событие конца документа
    */
    public void endDocument() throws SAXException{}
    
    /**
    *обрабатывает событие начала элемента <...>
    */
    public void startElement(String nameSpaceURI, String localName, String qName, Attributes attr) throws SAXException{
        displayBufferText();
        if("".equals(localName)){
            localName = qName;
            System.out.print("<" + localName);
        }
        if(attr!=null){
            //получаем количество аттрибутов элемента
            for (int i = 0; i < attr.getLength(); i++) {
                //получаем локальное имя аттрибута
                String aName = attr.getLocalName(i);
                if("".equals(aName)){
                    aName = attr.getQName(i);
                    System.out.print(" ");
                    //получаем значение аттрибута
                    System.out.print(aName + "=\"" + attr.getValue(i) + "\"");
                }
            }
        }
        System.out.print(">");        
    }

    private void displayBufferText() {
       if(stringBuffer!=null){
           System.out.print(stringBuffer.toString());
           stringBuffer=null;
       }
    }
    
    
    /**
     * обабатывает собитие конца элемента
     */
    public void endElement(String nameSpaceURI, String localName, String qName) throws SAXException{
        displayBufferText();
        if("".equals(localName)){
            localName = qName;
            System.out.print("</" + localName + ">");
        }
        
    }
    /**
     * обрабатывает собитие символьных данных текстового элемента <...>some_text<...>
     */
    public void characters(char [] buff, int offset, int len) throws SAXException{
        String s = new String(buff, offset, len);
        //если строковый буфер равен null, создаем новый
        if(stringBuffer==null){
            stringBuffer = new StringBuffer(s);
        }else{
            //добавляем символы в строковый буфер
            stringBuffer.append(s);
        }
        
    }
    
}
