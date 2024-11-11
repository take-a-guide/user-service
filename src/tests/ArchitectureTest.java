package br.com.takeaguide.userservice.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

@AnalyzeClasses(packages = "br.com.takeaguide.userservice")
public class ArchitectureTest {

    @ArchTest
    public static final ArchRule controllers_should_only_be_accessed_by_services =
        ArchRuleDefinition.classes()
            .that().resideInAPackage("..controllers..")
            .should().onlyBeAccessed().byAnyPackage("..services..");

    @ArchTest
    public static final ArchRule services_should_not_access_controllers =
        ArchRuleDefinition.noClasses()
            .that().resideInAPackage("..services..")
            .should().accessClassesThat().resideInAPackage("..controllers..");

    @ArchTest
    public static final ArchRule services_should_only_be_accessed_by_controllers_or_other_services =
        ArchRuleDefinition.classes()
            .that().resideInAPackage("..services..")
            .should().onlyBeAccessed().byAnyPackage("..controllers..", "..services..");

    @ArchTest
    public static final ArchRule no_cycles_in_package_dependencies =
        ArchRuleDefinition.noClasses()
            .should().dependOnClassesThat().resideInAPackage("..controllers..")
            .because("Controllers should not depend on each other");

    @ArchTest
    public static final ArchRule repositories_should_only_be_accessed_by_services =
        ArchRuleDefinition.classes()
            .that().resideInAPackage("..repositories..")
            .should().onlyBeAccessed().byAnyPackage("..services..");
}