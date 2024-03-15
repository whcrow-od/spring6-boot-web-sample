package ua.od.whcrow.samples.spring6.boot_web._global.web.aspect;

import org.aspectj.lang.annotation.Pointcut;

public interface Pointcuts extends ua.od.whcrow.samples.spring6.boot_web._global.aspect.Pointcuts {
	
	String PCD_TARGET_CONTROLLER = "@target(org.springframework.stereotype.Controller)";
	String PCD_TARGET_REST_CONTROLLER = "@target(org.springframework.web.bind.annotation.RestController)";
	
	String PCD_REQUEST_MAPPING = "@annotation(org.springframework.web.bind.annotation.RequestMapping)";
	String PCD_GET_MAPPING = "@annotation(org.springframework.web.bind.annotation.GetMapping)";
	String PCD_POST_MAPPING = "@annotation(org.springframework.web.bind.annotation.PostMapping)";
	String PCD_PUT_MAPPING = "@annotation(org.springframework.web.bind.annotation.PutMapping)";
	String PCD_DELETE_MAPPING = "@annotation(org.springframework.web.bind.annotation.DeleteMapping)";
	String PCD_PATCH_MAPPING = "@annotation(org.springframework.web.bind.annotation.PatchMapping)";
	String PCD_ANY_MAPPING = "(" +
			PCD_REQUEST_MAPPING + " || " +
			PCD_GET_MAPPING + " || " +
			PCD_POST_MAPPING + " || " +
			PCD_PUT_MAPPING + " || " +
			PCD_DELETE_MAPPING + " || " +
			PCD_PATCH_MAPPING + ")";
	
	// ---------------------- Controller pointcuts ----------------------
	
	@Pointcut("withinProject() && " + PCD_TARGET_CONTROLLER)
	static void targetController() {}
	
	@Pointcut("targetController() && " + PCD_PUBLIC_METHOD)
	static void controllerPublicMethod() {}
	
	@Pointcut("controllerPublicMethod() && " + PCD_REQUEST_MAPPING)
	static void controllerRequestMappingMethod() {}
	
	@Pointcut("controllerPublicMethod() && " + PCD_GET_MAPPING)
	static void controllerGetMappingMethod() {}
	
	@Pointcut("controllerPublicMethod() && " + PCD_POST_MAPPING)
	static void controllerPostMappingMethod() {}
	
	@Pointcut("controllerPublicMethod() && " + PCD_PUT_MAPPING)
	static void controllerPutMappingMethod() {}
	
	@Pointcut("controllerPublicMethod() && " + PCD_DELETE_MAPPING)
	static void controllerDeleteMappingMethod() {}
	
	@Pointcut("controllerPublicMethod() && " + PCD_PATCH_MAPPING)
	static void controllerPatchMappingMethod() {}
	
	@Pointcut("controllerPublicMethod() && " + PCD_ANY_MAPPING)
	static void controllerAnyMappingMethod() {}
	
	// ---------------------- REST Controller pointcuts ----------------------
	
	@Pointcut("withinProject() && " + PCD_TARGET_REST_CONTROLLER)
	static void targetRestController() {}
	
	@Pointcut("targetRestController() && " + PCD_PUBLIC_METHOD)
	static void restControllerPublicMethod() {}
	
	@Pointcut("restControllerPublicMethod() && " + PCD_REQUEST_MAPPING)
	static void restControllerRequestMappingMethod() {}
	
	@Pointcut("restControllerPublicMethod() && " + PCD_GET_MAPPING)
	static void restControllerGetMappingMethod() {}
	
	@Pointcut("restControllerPublicMethod() && " + PCD_POST_MAPPING)
	static void restControllerPostMappingMethod() {}
	
	@Pointcut("restControllerPublicMethod() && " + PCD_PUT_MAPPING)
	static void restControllerPutMappingMethod() {}
	
	@Pointcut("restControllerPublicMethod() && " + PCD_DELETE_MAPPING)
	static void restControllerDeleteMappingMethod() {}
	
	@Pointcut("restControllerPublicMethod() && " + PCD_PATCH_MAPPING)
	static void restControllerPatchMappingMethod() {}
	
	@Pointcut("restControllerPublicMethod() && " + PCD_ANY_MAPPING)
	static void restControllerAnyMappingMethod() {}
	
	// ---------------------- Any (Controller or REST Controller) pointcuts ----------------------
	
	@Pointcut("targetController() || targetRestController()")
	static void targetAnyController() {}
	
	@Pointcut("targetAnyController() && " + PCD_PUBLIC_METHOD)
	static void anyControllerPublicMethod() {}
	
	@Pointcut("anyControllerPublicMethod() && " + PCD_REQUEST_MAPPING)
	static void anyControllerRequestMappingMethod() {}
	
	@Pointcut("anyControllerPublicMethod() && " + PCD_GET_MAPPING)
	static void anyControllerGetMappingMethod() {}
	
	@Pointcut("anyControllerPublicMethod() && " + PCD_POST_MAPPING)
	static void anyControllerPostMappingMethod() {}
	
	@Pointcut("anyControllerPublicMethod() && " + PCD_PUT_MAPPING)
	static void anyControllerPutMappingMethod() {}
	
	@Pointcut("anyControllerPublicMethod() && " + PCD_DELETE_MAPPING)
	static void anyControllerDeleteMappingMethod() {}
	
	@Pointcut("anyControllerPublicMethod() && " + PCD_PATCH_MAPPING)
	static void anyControllerPatchMappingMethod() {}
	
	@Pointcut("anyControllerPublicMethod() && "+ PCD_ANY_MAPPING)
	static void anyControllerAnyMappingMethod() {}
	
}
