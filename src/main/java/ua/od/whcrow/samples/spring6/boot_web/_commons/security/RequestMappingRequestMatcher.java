package ua.od.whcrow.samples.spring6.boot_web._commons.security;

import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RequestMappingRequestMatcher implements RequestMatcher {
	
	private final RequestMappingInfo mappingInfo;
	private final List<AntPathRequestMatcher> pathMatchers;
	
	public RequestMappingRequestMatcher(@Nonnull RequestMappingInfo mappingInfo) {
		Assert.notNull(mappingInfo, "request mapping info");
		this.mappingInfo = mappingInfo;
		Set<RequestMethod> methods = mappingInfo.getMethodsCondition().getMethods();
		if (methods.isEmpty()) {
			methods = Collections.singleton(null);
		}
		this.pathMatchers = methods.stream()
				.flatMap(m -> mappingInfo.getPatternValues()
						.stream()
						.map(p -> new AntPathRequestMatcher(p, m.asHttpMethod().name())))
				.collect(Collectors.toList());
	}
	
	private boolean nonPathMatch(@Nonnull HttpServletRequest request) {
		boolean paramsMatch = mappingInfo.getParamsCondition().getMatchingCondition(request) != null;
		boolean headersMatch = mappingInfo.getHeadersCondition().getMatchingCondition(request) != null;
		boolean consumesMatch = mappingInfo.getConsumesCondition().getMatchingCondition(request) != null;
		return paramsMatch && headersMatch && consumesMatch;
	}
	
	@Override
	public boolean matches(@Nonnull HttpServletRequest request) {
		Assert.notNull(request, "request");
		boolean pathMatch = pathMatchers.stream().anyMatch(m -> m.matches(request));
		return pathMatch && nonPathMatch(request);
	}
	
	@Nonnull
	@Override
	public MatchResult matcher(@Nonnull HttpServletRequest request) {
		Assert.notNull(request, "request");
		MatchResult matchResult = MatchResult.notMatch();
		for (AntPathRequestMatcher pathMatcher : pathMatchers) {
			matchResult = pathMatcher.matcher(request);
			if (matchResult.isMatch()) {
				break;
			}
		}
		return matchResult.isMatch() && nonPathMatch(request) ? matchResult : MatchResult.notMatch();
	}
	
}
