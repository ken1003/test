
import com.ctcb.ldap.Attr;
import com.ctcb.ldap.Attributes;
import com.ctcb.ldap.LdapBasic;
public class LdapBasicTestMain {
	public static void main(String[] args) {
		LdapBasic l = new LdapBasic();
		String appId="EBMW_OP";
		String appSID="ou=" + appId + ",ou=APPs,o=CTCB";
		String appPWD="EBMW_OP1012";
		String LDAP_IP ="192.168.31.40";
		String appRoleId ="CCAdmin";

		
		String userId ="Z00040902";
		String userPWD ="Z00040902";
		//String userId ="Z00007270";
		//String userPWD ="Z00007270";
		String extraUserID ="Z00007270"; //瑞怡
		try {

			l.bind(LDAP_IP, 389, appSID, appPWD); 
			
			Attributes attrs = l.getUserProperty(userId, new String[] { "cn", "fullname", "workforceid" });
			dumpAttributes(attrs);
			
			String[] results = l.getUserRolesByAPP(userId, appId);
			System.out.println("getUserRolesByAPP ===========");
			dumpArray(results); 
			
			Attributes rAttrs = l.getRoleProperty(appId, appRoleId, new String[]{"cn", "fullname", "description"});
			System.out.println("getRoleProperty ===========");
			dumpAttributes(rAttrs);
			
			if(true) return;
			//取得使用者基本資料
			attrs = l.getUserProperty(userId, new String[] { "cn", "fullname", 
					"pwdChangedTime", "groupmembership" , "workforceID", "title" }); 
			
			//密碼是否過期，days是可自訂密碼有效天數  
			//過期時間為”pwdChangedtime”+days，其中”pwdChangedtime”的日期格式為UTC，必須轉換為台北時間(UTC+8)。
			boolean b = l.isPasswordExpired(userId, 60); 
			System.out.println("isPasswordExpired : " + b);
	
			//驗證使用者密碼 回傳 0:success 1:Login failed 2:Locked 3:Exception Message
			String vStr = l.verifyUserPwd(userId, userPWD);
			System.out.println("verifyUserPwd : " + vStr);
	
			//取得使用者兼職帳號  Return: Extra User ID  
			//兼職帳號,可多個  ctcbExtraPosCode != 0 且 workforceID =主職帳號的員編(workforceID)
			String[] rList5 = l.getExtraUserID(extraUserID);
			System.out.println("getExtraUserID ========== ");
			dumpArray(rList5);
			
			// 取得使用者被授予的應用程式角色ID  Return: 角色ID (多個角色ID字串)
			String[] results1 = l.getUserRolesByAPP(userId, appId);
			System.out.println("getUserRolesByAPP ===========");
			dumpArray(results); 
			
			//取得APP基本資料  回傳” Attributes”物件，必須可依欄位名稱取得資料並可取得Multiple Values
			Attributes aAttrs = l.getAppProperty(appId, new String[]{"cn", "fullname", "description"});
			System.out.println("getRoleProperty ===========");
			dumpAttributes(aAttrs);
	
			//取得角色基本資料  回傳” Attributes”物件，必須可依欄位名稱取得資料並可取得Multiple Values
			Attributes rAttrs1 = l.getRoleProperty(appId, appRoleId, new String[]{"cn", "fullname", "description"});
			System.out.println("getRoleProperty ===========");
			dumpAttributes(rAttrs1);
	
			//取得使用者的單位代碼
			String ouid = l.getUserOUID(userId);
			System.out.println("getUserOUID : " + ouid);
	
			//取得使用者歸屬的各階層組織(由下而上)的代碼
			String[] result = l.getUserOrgChainInfo(userId);
			System.out.println("getUserOrgChainInfo ===========");
			dumpArray(result); 
			
			//取得組織基本資料
			Attributes ouAttrs = l.getOUProperty("U00022036", new String[]{"ou", "fullname", "l", "preferredname", "title"});
			System.out.println("getOUProperty ===========");
			dumpAttributes(ouAttrs);
		}
		finally {
			l.unbind();
		}

	}

	public static void dumpArray(String[] ary) {
		for (int i = 0; ary != null && i < ary.length; i++) {
			System.out.println(ary[i]);
		}
	}

	public static void dumpAttributes(Attributes attrs) {
		if (attrs != null) {
			System.out.println("=== dump attributes start ===");
			String[] keys = attrs.getAttributeNames();
			for (int i = 0; i < keys.length; i++) {
				Attr attr = attrs.getAttribute(keys[i]);
				if (attr != null) {
					String[] values = attr.getAttrValues();
					for (int j = 0; attr != null && j < values.length; j++) {
						System.out.println(keys[i] + " : " + values[j]);
					}
				}
			}
			System.out.println("=== dump attributes end ===");
		}

	}

}
