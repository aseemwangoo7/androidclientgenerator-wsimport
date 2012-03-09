package fr.inria.arles.webService.androidClientGenerator;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.Template;


import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortImplGenerator {

    String templateFile="templates/PortImplGenerator.vm";

    public void generate(String path,String pack, String serviceName, 
            String portClass, List<OperationData> list) throws IOException {
        

        /*
         * setup
         */

        Velocity.init("velocity.properties");

        /*
         *  Make a context object and populate with the data.  This
         *  is where the Velocity engine gets the data to resolve the
         *  references (ex. $list) in the template
         */

        VelocityContext context = new VelocityContext();
        context.put("package", pack);
        context.put("portClass", portClass);      
        context.put("list",list);  
        context.put("returnVal", AllowedTypes.returnVal) ;
        context.put("soapClass", AllowedTypes.soapClass) ;
        
       


        /*
         *  get the Template object.  This is the parsed version of your
         *  template input file.  Note that getTemplate() can throw
         *   ResourceNotFoundException : if it doesn't find the template
         *   ParseErrorException : if there is something wrong with the VTL
         *   Exception : if something else goes wrong (this is generally
         *        indicative of as serious problem...)
         */

        Template template = null;
        template = Velocity.getTemplate(templateFile);

        /*
         *  Now have the template engine process your template using the
         *  data placed into the context.  Think of it as a  'merge'
         *  of the template and the data to produce the output stream.
         */

        BufferedWriter writer = writer = new BufferedWriter(
                new FileWriter(path+portClass+"Impl.java"));

        if (template != null) {
            template.merge(context, writer);
        }

        /*
         *  flush and cleanup
         */

        writer.flush();
        writer.close();

    }
    
}
