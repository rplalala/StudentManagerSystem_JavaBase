package com.ding.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * 方法1,2是对xml的解析辅助，适用于任何情况
 * xml读取操作一般分为：
 * 解析 -> 操作
 * xml更新操作一般分为：
 * 解析 -> 操作 ->写入
 * 其中 解析 和 写入 可以通过其帮你完成，操作 可以自己撰写
 * 用方法1,2和自己撰写的中间操作就可以完成对xml的所有操作
 * <p>
 * 方法3,4,5,6是对xml的读取、新增、修改、删除操作。
 * 根据不同的xml文件和项目要求，需要对 方法进行一定的修改。
 * 仅供参考，必要时自己撰写中间操作
 */
public class XMLUtils {

    private String pathName; //xml文件所在路径

    public XMLUtils() {
    }

    //在构造XMLUtils对象时，传入xml文件所在路径pathName
    public XMLUtils(String pathName) {
        this.pathName = pathName;
    }

    /**
     * 1
     * 解析XML文件，得到document对象
     */
    public Document xmlParse() {
        // 创建DOM4J解析器
        SAXReader saxReader = new SAXReader();
        Document document = null;

        // 通过解析器去解读XML文件
        try {
            document = saxReader.read(pathName);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }


    /**
     * 2
     * 把对document的操作写入xml文档
     * <p>
     * document：通过解析XML文件，获得的document对象
     */
    public void xmlWrite(Document document) {
        // 格式化输出
        OutputFormat of = OutputFormat.createPrettyPrint();
        of.setEncoding("utf-8");

        // 写入的操作
        XMLWriter xmlWriter = null;
        try {
            xmlWriter = new XMLWriter(new FileWriter(pathName), of);
            xmlWriter.write(document);//将document的操作写入pathName路径下的xml文档
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (xmlWriter != null) {
                    xmlWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 3
     * 读取xml数据
     * 返回List<Element>集合
     * <p>
     * <p>
     * 需要根据xml文档更改，并不是写死的
     */
    public List<Map<String, String>> xmlRead(Document document) {
        List<Element> nodes = document.selectNodes("/Admins/Admin");//需要看情况修改
        List<Map<String, String>> list = new ArrayList<>();
        for (Element node : nodes) {
            Map<String, String> map = new HashMap<>();
            map.put("userName", node.elementText("userName"));//需要看情况修改
            map.put("userPassword", node.elementText("userPassword"));//需要看情况修改
            list.add(map);
        }
        return list;
    }

    /**
     * 4
     * 添加xml数据
     * <p>
     * <p>
     * values：给xml中key对应的value添加的值
     * <p>
     * <p>
     * 需要根据xml文档更改，并不是写死的
     */
    public boolean xmlAdd(Document document, String[] values) {
        Element rootNode = document.getRootElement();
        Element childNode = rootNode.addElement("Admin");//需要看情况修改
        String[] keys = {"userName", "userPassword"};//需要看情况修改
        for (int i = 0; i < keys.length; i++) {
            childNode.addElement(keys[i]).addText(values[i]);
        }
        xmlWrite(document);//执行写入操作
        return true;
    }

    /**
     * 5
     * 修改xml数据
     * 返回是否修改成功(xml中是否有满足修改条件的数据)
     * <p>
     * <p>
     * values：给xml中key对应的value修改的值
     * userName：需要修改的管理员 的用户名
     * <p>
     * <p>
     * 需要根据xml文档更改，并不是写死的
     */
    public boolean xmlUpdate(Document document, String[] values, String userName) {
        List<Element> nodes = document.selectNodes("/Admins/Admin");//需要看情况修改
        boolean judge = false;
        for (Element node : nodes) {
            if (node.elementText("userName").equals(userName)) {//需要看情况修改
                judge = true;
                node.element("userName").setText(values[0]);//需要看情况修改
                node.element("userPassword").setText(values[1]);//需要看情况修改
                xmlWrite(document);//执行写入操作
                break;
            }
        }
        return judge;
    }

    /**
     * 6
     * 删除xml数据
     * 返回是否删除成功(xml中是否有满足删除条件的数据)
     * <p>
     * <p>
     * userName：需要修改的管理员 的用户名
     * <p>
     * <p>
     * 需要根据xml文档更改，并不是写死的
     */
    public boolean xmlDelete(Document document, String userName) {
        List<Element> nodes = document.selectNodes("/Admins/Admin");//需要看情况修改
        boolean judge = false;
        for (Element node : nodes) {
            if (node.elementText("userName").equals(userName)) {//需要看情况修改
                judge = true;
                node.getParent().remove(node);//删除该节点
                xmlWrite(document);//执行写入操作
                break;
            }
        }
        return judge;
    }
}
