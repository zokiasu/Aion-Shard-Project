package com.aionemu.gameserver.utils.xml;

import com.aionemu.gameserver.dataholders.StaticData;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class SchemaGen {

    public static void main(String[] args) throws Exception {
        final File baseDir = new File("./data/static_data");

        class MySchemaOutputResolver extends SchemaOutputResolver {

            @Override
            public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
                return new StreamResult(new File(baseDir, "static_data1.xsd"));
            }
        }
        JAXBContext context = JAXBContext.newInstance(StaticData.class);
        context.generateSchema(new MySchemaOutputResolver());
    }
}
