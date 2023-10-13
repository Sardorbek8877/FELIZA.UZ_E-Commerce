package uz.feliza.felizabackend.sms;

import feign.MethodMetadata;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class CustomSpringMvcContract extends SpringMvcContract {

    @Override
    protected void processAnnotationOnMethod(MethodMetadata data, Annotation methodAnnotation, Method method) {
        if (methodAnnotation instanceof RequestMapping requestMapping) {
            if (requestMapping.method().length > 0) {
                String httpMethod = requestMapping.method()[0].name();
                data.template().method(httpMethod);
            }
            if (requestMapping.value().length > 0) {
                String uri = requestMapping.value()[0];
                if (!uri.startsWith("/")) {
                    uri = "/" + uri;
                }
                data.template().uri(uri);
            }
        }
        super.processAnnotationOnMethod(data, methodAnnotation, method);
    }
}
