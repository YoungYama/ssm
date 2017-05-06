package com.yzz.log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yzz.entity.SysUser;
import com.yzz.util.ConstantUtil;

@Aspect
@Component
public class LogAspect {

	private HttpServletRequest request;
	private HttpSession session;
	private SysUser sysUser;

	@Before("within(com.yzz.ctrl..*) && @annotation(logInfo)")
	public void before(final LogInfo logInfo) {
		request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		session = request.getSession();
	}

	// 此注解是后置增强，方法执行成功后会执行
	@AfterReturning("within(com.yzz.ctrl..*) && @annotation(logInfo)")
	public void OperationSuccessLog(final JoinPoint joinPoint, final LogInfo logInfo) {
		sysUser = ((SysUser) session.getAttribute(ConstantUtil.LOGINING_SYSUSER));

		String modelTypeName = logInfo.modelTypeName();// 要执行的操作的模块名称比如：人员管理模块
		String operationTypeName = logInfo.operationTypeName();// 要执行的操作类型名称比如：添加
		String operationContent = logInfo.operationContent();// 要执行的具体操作比如：XX添加了用户

		String targetName = joinPoint.getTarget().getClass().getName();// 获取目标类名
		String methodName = joinPoint.getSignature().getName(); // 获取目标方法名
		Object[] args = joinPoint.getArgs();// 获取目标方法参数
		System.out.println(" modelTypeName:" + modelTypeName + " operationTypeName:" + operationTypeName
				+ " operationContent:" + operationContent + " targetName:" + targetName + " methodName:" + methodName
				+ " args:" + args);
	}

	// 该方法体为异常通知，当目标方法出现异常时，执行该方法体
	@AfterThrowing(pointcut = "within(com.yzz.ctrl..*) && @annotation(logInfo)", throwing = "e")
	public void OperationFailureLog(final JoinPoint joinPoint, final LogInfo logInfo, Exception e) {
		sysUser = ((SysUser) session.getAttribute(ConstantUtil.LOGINING_SYSUSER));

		String modelTypeName = logInfo.modelTypeName();// 要执行的操作的模块名称比如：人员管理模块
		String operationTypeName = logInfo.operationTypeName();// 要执行的操作类型名称比如：添加
		String operationContent = logInfo.operationContent();// 要执行的具体操作比如：XX添加了用户

		String targetName = joinPoint.getTarget().getClass().getName();// 获取目标类名
		String methodName = joinPoint.getSignature().getName(); // 获取目标方法名
		Object[] args = joinPoint.getArgs();// 获取目标方法参数
		System.out.println(" modelTypeName:" + modelTypeName + " operationTypeName:" + operationTypeName
				+ " operationContent:" + operationContent + " targetName:" + targetName + " methodName:" + methodName
				+ " args:" + args);
		System.out.println("-------------" + e.getMessage());
	}
	
	
	
	 /** 
     * 在核心业务执行前执行，不能阻止核心业务的调用。 
     * @param joinPoint 
     */  
    public void doBefore(JoinPoint joinPoint) {  
        System.out.println("-----doBefore().invoke-----");  
        System.out.println(" 此处意在执行核心业务逻辑前，做一些安全性的判断等等");  
        System.out.println(" 可通过joinPoint来获取所需要的内容");  
        System.out.println("-----End of doBefore()------");  
    }  
      
    /** 
     * 手动控制调用核心业务逻辑，以及调用前和调用后的处理, 
     *  
     * 注意：当核心业务抛异常后，立即退出，转向After Advice 
     * 执行完毕After Advice，再转到Throwing Advice 
     * @param pjp 
     * @return 
     * @throws Throwable 
     */  
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {  
        System.out.println("-----doAround().invoke-----");  
        System.out.println(" 此处可以做类似于Before Advice的事情");  
          
        //调用核心逻辑  
        Object retVal = pjp.proceed();  
          
        System.out.println(" 此处可以做类似于After Advice的事情");  
        System.out.println("-----End of doAround()------");  
        return retVal;  
    }  
  
    /** 
     * 核心业务逻辑退出后（包括正常执行结束和异常退出），执行此Advice 
     * @param joinPoint 
     */  
    public void doAfter(JoinPoint joinPoint) {  
        System.out.println("-----doAfter().invoke-----");  
        System.out.println(" 此处意在执行核心业务逻辑之后，做一些日志记录操作等等");  
        System.out.println(" 可通过joinPoint来获取所需要的内容");  
        System.out.println("-----End of doAfter()------");  
    }  
      
    /** 
     * 核心业务逻辑调用正常退出后，不管是否有返回值，正常退出后，均执行此Advice 
     * @param joinPoint 
     */  
    public void doReturn(JoinPoint joinPoint) {  
        System.out.println("-----doReturn().invoke-----");  
        System.out.println(" 此处可以对返回值做进一步处理");  
        System.out.println(" 可通过joinPoint来获取所需要的内容");  
        System.out.println("-----End of doReturn()------");  
    }  
      
    /** 
     * 核心业务逻辑调用异常退出后，执行此Advice，处理错误信息 
     * @param joinPoint 
     * @param ex 
     */  
    public void doThrowing(JoinPoint joinPoint,Throwable ex) {  
        System.out.println("-----doThrowing().invoke-----");  
        System.out.println(" 错误信息："+ex.getMessage());  
        System.out.println(" 此处意在执行核心业务逻辑出错时，捕获异常，并可做一些日志记录操作等等");  
        System.out.println(" 可通过joinPoint来获取所需要的内容");  
        System.out.println("-----End of doThrowing()------");  
    }  

}
