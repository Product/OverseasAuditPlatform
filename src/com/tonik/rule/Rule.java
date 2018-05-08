package com.tonik.rule;

/**
 * 商品数据抓取规则类
 * 
 * @author bchen
 * 
 */
public class Rule
{
	// 默认一次最大抓取9999页;
	public static int MAX_FETCH_PAGE = 99999;
	
	/*
	 * Paging info
	 */
	private PagingApproachType pagingApproachType = PagingApproachType.DYNAMIC;
	//URL类别:动态url，静态相对地址，静态绝对地址
	public static enum PagingApproachType {
	    //翻页由动态url实现
        DYNAMIC("DYNAMIC"), 
        //翻页由静态相对地址实现
        STATIC_REL("STATIC_REL"), 
        //翻页由静态绝对地址实现
        STATIC_ABS("STATIC_ABS");
        
        // 成员变量
        private String name;
        
	    // 构造方法
        private PagingApproachType(String name) {
            this.name = name;
        }
    }
	
	//动态url,翻页步长
	private int dynamicStep = 1;
	
	/*
	 * Mapping info
	 */
	//需要获取的商品信息字段 
	private String[] mappingFields;
	//需要获取的商品信息字段 对应的路径
	private String[] mappingPaths;
	//需要获取的商品信息字段 对应的路径 的使用方法
	private ElementLocateType[] mappingPathLocateTypes;
	public static enum ElementLocateType {
		CLASS("CLASS"), 
		ID("ID"), 
		SELECTION("SELECTION"), 
		TEXT("TEXT"),
	    PARAM("PARAM"),//没有下一页，直接使用参数翻页
	    LINK_TEXT("LINK_TEXT"),
	    PARTIAL_LINK_TEXT("PARTIAL_LINK_TEXT"),
	    XPATH("XPATH");
	    
	    // 成员变量
        private String name;
        
        // 构造方法
        private ElementLocateType(String name) {
            this.name = name;
        }
    }
	private CharacterLocation[] mappingValueLocations;
	
	/*
	 * Next page, NextPageURL and Paging end checking.
	 */
	//定位“下一页” element
	private ElementLocateType nextPageLocateType = ElementLocateType.CLASS;
	
	// 使用CLASS, ID, SELECTION定位“下一页”element时的路径， 
	private String nextPagePath;
	//使用TEXT定位“下一页”时， element中包含的特征信息
	private String nextPageCharacter = "下一页";
	
	//next page url
//	private CharacterLocation nextPageUrlLocation = CharacterLocation.HREF;
	
	//最后一个页面中 “下一页” element中用来判定这是最后一页的特征信息
	private String endingPageCharacter;
	//最后一个页面中 “下一页” element中用来判定这是最后一页的特征信息 的位置
	private CharacterLocation endingPageCharacterLocation;
	public static enum CharacterLocation {
	  //class 中包含有特征信息
        CLASS("CLASS"), 
        //text中包含有特征信息
        TEXT("TEXT"),
        //href中包含有特征信息
        HREF("HREF"),
        //href中包含有相对地址
        HREF_REL("HREF_REL"),
        //title中包含有特征信息
        TITLE("TITLE"),
        //参数中包含有特征信息
        PARAM("PARAM"),
        
        DISABLED("DISABLED"),
        
        HTML("HTML");
	    
	    // 成员变量
        private String name;
        
        // 构造方法
        private CharacterLocation(String name) {
            this.name = name;
        }
    }
	
	/*
	 * 链接
	 */
	private String entryUrl;
	private String lastPageUrl;
	
	//参数集合
	private String[] entryUrlParams = new String[]{};
	//参数对应的值
	private String[] entryUrlParamValues = new String[]{};
	//请求的类型，默认GET
	private RequestMethod requestMethod = RequestMethod.GET ; 
	public static enum RequestMethod{
		GET("GET"),
		POST("POST");
		
		// 成员变量
        private String name;
        
        // 构造方法
        private RequestMethod(String name) {
            this.name = name;
        }
	}
	
	//对返回的HTML，定位产品列表所用的标签，请先设置productsLocateType
	private String productsPath;
	//定位产品列表标签的方式
	private ElementLocateType productsLocateType = ElementLocateType.ID ;
	
	/*
	 *指定用于分页的参数名称
	 */
	private String pagingParam = "";
	
	

	public Rule()
	{
	}

	
	public Rule(String url, String[] params, String[] values,
			String productsPath, ElementLocateType productsLocateType, RequestMethod requestMethod, String pageParam)
	{
		super();
		this.entryUrl = url;
		this.entryUrlParams = params;
		this.entryUrlParamValues = values;
		this.productsPath = productsPath;
		this.productsLocateType = productsLocateType;
		this.requestMethod = requestMethod;
		this.pagingParam = pageParam;
	}

	
	public PagingApproachType getPagingApproachType() {
		return pagingApproachType;
	}


	public void setPagingApproachType(PagingApproachType pagingApproachType) {
		this.pagingApproachType = pagingApproachType;
	}


	public int getDynamicStep() {
		return dynamicStep;
	}


	public void setDynamicStep(int dynamicStep) {
		this.dynamicStep = dynamicStep;
	}



	public String getEntryUrl() {
		return entryUrl;
	}


	public void setEntryUrl(String entryUrl) {
		this.entryUrl = entryUrl;
	}


	public String getLastPageUrl()
    {
        return lastPageUrl;
    }


    public void setLastPageUrl(String lastPageUrl)
    {
        this.lastPageUrl = lastPageUrl;
    }


    public ElementLocateType getNextPageLocateType() {
		return nextPageLocateType;
	}


	public void setNextPageLocateType(ElementLocateType nextPageLocateType) {
		this.nextPageLocateType = nextPageLocateType;
	}


	public String getNextPagePath() {
		return nextPagePath;
	}


	public void setNextPagePath(String nextPagePath) {
		this.nextPagePath = nextPagePath;
	}


	public String getNextPageCharacter() {
		return nextPageCharacter;
	}


	public void setNextPageCharacter(String nextPageCharacter) {
		this.nextPageCharacter = nextPageCharacter;
	}


	public String getEndingPageCharacter() {
		return endingPageCharacter;
	}


	public void setEndingPageCharacter(String endingPageCharacter) {
		this.endingPageCharacter = endingPageCharacter;
	}


	public CharacterLocation getEndingPageCharacterLocation() {
		return endingPageCharacterLocation;
	}


	public void setEndingPageCharacterLocation(CharacterLocation endingPageCharacterLocation) {
		this.endingPageCharacterLocation = endingPageCharacterLocation;
	}



	public String[] getEntryUrlParams() {
		return entryUrlParams;
	}


	public void setEntryUrlParams(String[] entryUrlParams) {
		this.entryUrlParams = entryUrlParams;
	}


	public String[] getEntryUrlParamValues() {
		return entryUrlParamValues;
	}


	public void setEntryUrlParamValues(String[] entryUrlParamValues) {
		this.entryUrlParamValues = entryUrlParamValues;
	}


	public String getPagingParam() {
		return pagingParam;
	}


	public void setPagingParam(String pagingParam) {
		this.pagingParam = pagingParam;
	}


	public String getProductsPath() {
		return productsPath;
	}


	public void setProductsPath(String productsPath) {
		this.productsPath = productsPath;
	}


	public ElementLocateType getProductsLocateType() {
		return productsLocateType;
	}


	public void setProductsLocateType(ElementLocateType productsLocateType) {
		this.productsLocateType = productsLocateType;
	}



	public String[] getMappingFields() {
		return mappingFields;
	}


	public void setMappingFields(String[] mappingFields) {
		this.mappingFields = mappingFields;
	}


	public String[] getMappingPaths() {
		return mappingPaths;
	}


	public void setMappingPaths(String[] mappingPaths) {
		this.mappingPaths = mappingPaths;
	}


	public ElementLocateType[] getMappingPathLocateTypes() {
		return mappingPathLocateTypes;
	}


	public void setMappingPathLocateTypes(ElementLocateType[] mappingPathLocateTypes) {
		this.mappingPathLocateTypes = mappingPathLocateTypes;
	}



	public CharacterLocation[] getMappingValueLocations() {
		return mappingValueLocations;
	}


	public void setMappingValueLocations(CharacterLocation[] mappingValueLocations) {
		this.mappingValueLocations = mappingValueLocations;
	}


	public RequestMethod getRequestMethod() {
		return requestMethod;
	}


	public void setRequestMethod(RequestMethod requestMethod) {
		this.requestMethod = requestMethod;
	}


	public boolean turnToNextPage(String url) {
		boolean result = false;
		
		if(pagingApproachType.equals(PagingApproachType.DYNAMIC)){
			int pageParamIndex = getPageParamIndex();
			int curPage = 0;
			if (pageParamIndex >= 0) {
			    try
                {
			        curPage = Integer.parseInt(this.entryUrlParamValues[pageParamIndex]);
                } catch (Exception e)
                {
                    // TODO: Log error info.
                    return false;
                }
                this.entryUrlParamValues[pageParamIndex] = String.valueOf(curPage + dynamicStep);
				result = true;
			}
		}else{
			this.entryUrl = url;
			result = true;
		}
		return result;
	}

	public int getPageParamIndex(){
		int result = -1;
		for(int i=0;i<this.entryUrlParams.length;i++){
			if(this.entryUrlParams[i] !=null && this.entryUrlParams[i].equalsIgnoreCase(this.pagingParam)){
				result = i;
				break;
			}
		}
		return result;
	}
	
	public String getCurrentFullUrl(){
        StringBuffer result = new StringBuffer();
        
        String baseUrl = this.getEntryUrl();
        if(baseUrl == null){
            return null;
        }
        
        int paramLocation = baseUrl.indexOf("?");
        if(paramLocation > 0){
            baseUrl = baseUrl.substring(0, paramLocation);
        }
        result.append(baseUrl);
        
        boolean isFirst = true;
        for(int i=0;i<this.entryUrlParams.length;i++){
            if(this.entryUrlParams[i] != null && this.entryUrlParamValues[i] != null){
                if(isFirst){
                    result.append("?");
                    isFirst = false;
                }else{
                    result.append("&");
                }
                result.append(this.entryUrlParams[i]);
                result.append("=");
                result.append(this.entryUrlParamValues[i]);
            }
        }
        return result.toString();
    }

}

