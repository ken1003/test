
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ebmw.dto.burgerMenu.BurgerMenuDto;
import ebmw.form.TestBean;
import ebmw.model.dto.BurgerMenu;
import ebmw.model.dto.I18N;
import ebmw.util.JsonUtils;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Integer[] sortList = new Integer[4];
		sortList[0] = 100;
		sortList[1] = 200;
		sortList[2] = 10;
		sortList[3] = 150;
		
		Arrays.sort(sortList);
		
		for (Integer s : sortList){
			System.out.println(s);
		}
		
		String[] titleArr = "測試,{123:123}".split(",");
		System.out.println(titleArr[0]);
		
		BurgerMenu dto = new BurgerMenu();
		dto.setMenuId("1234");
		I18N i18n = new I18N();
		i18n.setI18nKey("18NKey");
		i18n.setI18nValue("AAAA");
		dto.setI18N(i18n);
		
		HashMap<String, String> yesNoMap = new LinkedHashMap<String, String>();
		yesNoMap.put("0", "否");
		yesNoMap.put("1", "是");
		String yesNoJson = JsonUtils.parseMapToJson(yesNoMap);
		
		TestBean t = new TestBean();
		t.setMessage("title" + "-" + yesNoJson);
		List<String> testList = new ArrayList<String>();
		testList.add("AAA");
		testList.add("BBB");
		testList.add("CCC");
		t.setTestList(testList);
		
		TestBean tNew = new TestBean();
		tNew.setMessage("new");
		List<String> testList1 = new ArrayList<String>();
		testList1.add("5");
		testList1.add("10");
		testList1.add("15");
		tNew.setTestList(testList1);
		
		TestBean tOld = new TestBean();
		tOld.setMessage("old");
		List<String> testList2 = new ArrayList<String>();
		testList2.add("5");
		testList2.add("10");
		testList2.add("15");
		tOld.setTestList(testList2);
		
		List<BurgerMenuDto> list = new ArrayList<BurgerMenuDto>();
		BurgerMenuDto b = new BurgerMenuDto();
		b.setOrderNo("5");
		list.add(b);
		b = new BurgerMenuDto();
		b.setOrderNo("10");
		list.add(b);
		b = new BurgerMenuDto();
		b.setOrderNo("15");
		list.add(b);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("title", list);
//		map.put("oldData", tOld);
//		map.put("newData", tNew);
		
		
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			
			String jsonValue = mapper.writeValueAsString(map);
			System.out.println(jsonValue);
			TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {};
			
			TypeReference<HashMap<String,LinkedHashMap<String, Object>>> typeRef1 = new TypeReference<HashMap<String,LinkedHashMap<String, Object>>>() {};
			
//			HashMap<String,LinkedHashMap<String, Object>> dataMap = mapper.readValue(jsonValue, typeRef1);
			HashMap<String, Object> dataMap = mapper.readValue(jsonValue, typeRef);
			
			if (dataMap.get("title") instanceof java.util.Map) {
				LinkedHashMap<String, Object> titleMap = (LinkedHashMap<String, Object>) dataMap.get("title");
				Iterator<Map.Entry<String, Object>> it = titleMap.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<String, Object> entry = it.next();
					if (entry.getValue() != null) {
						String key = entry.getKey();
						if (titleMap.get(key) instanceof java.util.List){
							List listVal = (List) titleMap.get(key);
							for (Object o : listVal){
								System.out.println("List:" + o);
							}
						} else if (titleMap.get(key) instanceof java.util.Map){
							Map mapR = (Map) titleMap.get(key);
							
							Iterator<Map.Entry<String, Object>> it1 = mapR.entrySet().iterator();
							while (it1.hasNext()) {
								Map.Entry<String, Object> entry1 = it1.next();
								if (entry1.getValue() != null) {
									System.out.println("Map:" + entry1.getValue());
								}
							}
							
						} else {
							System.out.println("String:" + titleMap.get(key));
						}
						
//						System.out.println(titleMap.get(key) + ":" + oldMap.get(key) + ":" + newMap.get(key));
					}
					
				}
			} else if (dataMap.get("title") instanceof java.util.List) {
				List listVal = (List) dataMap.get("title");
				for (Object o : listVal){
					if (o instanceof java.util.Map){
						Map mapR = (Map) o;
						Iterator<Map.Entry<String, Object>> it1 = mapR.entrySet().iterator();
						BurgerMenuDto burger = map2Bean(mapR, BurgerMenuDto.class);
						System.out.println("DTO : " + burger.getOrderNo());
						while (it1.hasNext()) {
							Map.Entry<String, Object> entry1 = it1.next();
							if (entry1.getValue() != null) {
								System.out.println("Map:" + entry1.getValue());
							}
						}
					}
				}
			}
				
			
//			LinkedHashMap<String, Object> oldMap = dataMap.get("oldData");
//			LinkedHashMap<String, Object> newMap = dataMap.get("newData");
			
			
			
//			TestBean retMap = result.get("old");
//			LinkedHashMap<String, Object> newMap = result.get("newT");
			
//			BurgerMenuDto r = map2BeanT(result.get("newT"), BurgerMenuDto.class);
			
//			System.out.println(r.getMenuId());
			
//			TestBean r = retMap;
//			System.out.println(r.getName());
//			System.out.println(r.getMessage());
//			System.out.println(r.getTestList().get(0));
//			System.out.println(r.getTestmap().get("eeeee"));
			
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public static <T, K, V> T map2BeanT(HashMap<K, V> mp, Class<T> beanCls)
			throws Exception, IllegalArgumentException, InvocationTargetException {
		T t = null;
		try {

			BeanInfo beanInfo = Introspector.getBeanInfo(beanCls);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

			t = beanCls.newInstance();

			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				if (mp.containsKey(key)) {
					Object value = mp.get(key);
					Method setter = property.getWriteMethod();// Java中提供了用来访问某个属性的
																// getter/setter方法
					setter.invoke(t, value);
				}
			}

		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return t;
	}
	
	public static <T> T map2Bean(Map map, Class<T> targetClass) {  
        T bean = null;  
        try {  
        	ConvertUtils.register(new DateConverter(null), Date.class);
        	ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
            bean = targetClass.newInstance();  
            BeanUtils.copyProperties(bean, map);  
        } catch (InstantiationException e) {  
            e.printStackTrace();  
        } catch (IllegalAccessException e) {  
            e.printStackTrace();  
        } catch (InvocationTargetException e) {  
            e.printStackTrace();  
        }  
        return bean;  
    }

}
