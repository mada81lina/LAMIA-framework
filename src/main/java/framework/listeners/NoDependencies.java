package framework.listeners;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by IlieV on 10.03.2016.
 * Used for ignoring test dependencies.
 * Add in xml or use class annotation.
 */

public class NoDependencies implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation iTestAnnotation, Class aClass, Constructor constructor, Method method) {
        iTestAnnotation.setDependsOnGroups(null);
        iTestAnnotation.setDependsOnMethods(null);
    }
}
