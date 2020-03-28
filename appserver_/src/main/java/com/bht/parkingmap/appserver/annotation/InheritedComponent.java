package com.bht.parkingmap.appserver.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * This annotation using only for abstract class or interface
 * which children (who extends this abstract class or implements this interface)
 * need to be component or need to be injected on application starts !
 * <code>@Component</code> annotation of spring, marks that this class will be injected
 * <code>@Inherited</code> annotation of java lang, marks that the above annotation will be inherited into children: @Component
 *
 * @author bht
 */
@Component
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InheritedComponent {
}