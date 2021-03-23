package co.com.sofka.application.init;

import co.com.sofka.application.ServiceBuildException;
import co.com.sofka.business.annotation.ExtensionService;
import co.com.sofka.business.generic.ServiceBuilder;
import io.github.classgraph.AnnotationClassRef;
import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.AnnotationParameterValueList;
import io.github.classgraph.ClassInfo;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

/**
 * The type Base application executor.
 */
public class BaseApplicationExecutor {
    private BaseApplicationExecutor() {

    }

    /**
     * Get command type string.
     *
     * @param packageUseCase  the package use case
     * @param handleClassInfo the handle class info
     * @param annotationInfo  the annotation info
     * @return the string
     */
    public static String getCommandType(String packageUseCase, ClassInfo handleClassInfo, AnnotationInfo annotationInfo) {
        return Optional.ofNullable(annotationInfo).map(annotation -> {
            AnnotationParameterValueList paramVals = annotation.getParameterValues();
            return (String) paramVals.getValue("name");
        }).orElse(handleClassInfo
                .loadClass()
                .getCanonicalName()
                .toLowerCase().replace(packageUseCase + ".", "")
        );
    }

    /**
     * Gets aggregate.
     *
     * @param annotationInfo the annotation info
     * @return the aggregate
     */
    public static String getAggregate(AnnotationInfo annotationInfo) {
        return Optional.ofNullable(annotationInfo).map(annotation -> {
            AnnotationParameterValueList paramVals = annotation.getParameterValues();
            return (String) paramVals.getValue("aggregate");
        }).orElse("default");
    }

    /**
     * Gets service builder.
     *
     * @param serviceBuilder  the service builder
     * @param handleClassInfo the handle class info
     * @return the service builder
     */
    public static ServiceBuilder getServiceBuilder(ServiceBuilder serviceBuilder, ClassInfo handleClassInfo) {
        AnnotationInfo annotationInfo = handleClassInfo.getAnnotationInfo(ExtensionService.class.getName());
        return Optional.ofNullable(annotationInfo).map(annotation -> {
            AnnotationParameterValueList paramVals = annotation.getParameterValues();
            var list = (Object[]) paramVals.getValue("value");
            for (Object o : list) {
                try {
                    var ref = (AnnotationClassRef) o;
                    if (!serviceBuilder.exist(ref.loadClass())) {
                        serviceBuilder.addService(ref.loadClass().getDeclaredConstructor().newInstance());
                    }
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new ServiceBuildException(e);
                }
            }
            return serviceBuilder;
        }).orElse(serviceBuilder);
    }

}
