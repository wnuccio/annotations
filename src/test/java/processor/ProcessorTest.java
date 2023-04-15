package processor;

import annotations.classes.AnnotatedClass;
import annotations.classes.AnnotatedClassWithDifferentBase;
import annotations.classes.BaseType;
import annotations.classes.NotAnnotatedClass;
import annotations.processor.Processor;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProcessorTest {

    @Test
    void processor_recognizes_an_annotated_class() {
        Processor processor = new Processor("annotations.classes");

        boolean annotated = processor.isAnnotated(AnnotatedClass.class);

        assertTrue(annotated);
    }

    @Test
    void processor_returns_only_annotated_classes() {
        Processor processor = new Processor("annotations.classes");

        Set<Class<?>> annotatedClasses = processor.findAnnotatedClasses();

        assertEquals(2, annotatedClasses.size());
        Iterator<Class<?>> iterator = annotatedClasses.iterator();
        Class<?> firstClass = iterator.next();
        Class<?> secondClass = iterator.next();
        assertTrue(firstClass.equals(AnnotatedClass.class) || firstClass.equals(AnnotatedClassWithDifferentBase.class));
        assertTrue(secondClass.equals(AnnotatedClass.class) || secondClass.equals(AnnotatedClassWithDifferentBase.class));
    }

    @Test
    void processor_returns_an_instance_of_first_annotated_class() {
        Processor processor = new Processor("annotations.classes");

        Optional<AnnotatedClass> annotatedClass = processor.createAnnotatedClassIfExists(AnnotatedClass.class);

        assertTrue(annotatedClass.isPresent());
    }

    @Test
    void processor_returns_no_instance_if_no_annotated_class_exists() {
        Processor processor = new Processor("annotations.classes");

        Optional<NotAnnotatedClass> annotatedClass = processor.createAnnotatedClassIfExists(NotAnnotatedClass.class);

        assertTrue(annotatedClass.isEmpty());
    }

    @Test
    void processor_returns_an_instance_of_all_annotated_class_with_common_base() {
        Processor processor = new Processor("annotations.classes");

        List<BaseType> annotatedClass = processor.createAnnotatedClasses(BaseType.class);

        assertEquals(1, annotatedClass.size());
    }

}