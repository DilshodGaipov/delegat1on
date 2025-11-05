package com.ddcorp.delegat1on.impl;
import com.atlassian.sal.api.pluginsettings.*; import com.ddcorp.delegat1on.api.ConfigService;
import com.google.gson.*; import javax.inject.*; import java.util.*; import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned; import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
@Scanned @ExportAsService({ConfigService.class}) @Named("delegationConfig")
public class ConfigServiceImpl implements ConfigService {
  @ComponentImport private final PluginSettingsFactory psf;
  private static final String K_CATS="com.ddcorp.delegat1on:cats";
  private static final String K_PERM="com.ddcorp.delegat1on:perms";
  private final Gson g=new Gson();
  @Inject public ConfigServiceImpl(PluginSettingsFactory psf){ this.psf=psf; ensureDefaults(); }
  private PluginSettings ps(){ return psf.createGlobalSettings(); }
  private void ensureDefaults(){
    if (ps().get(K_CATS)==null) ps().put(K_CATS, g.toJson(new ArrayList<String>()));
    if (ps().get(K_PERM)==null){
      Map<String,Boolean> def=new HashMap<>(); def.put("delegateAssignee", true); def.put("delegateReporter", true); def.put("postFunctionAssign", true);
      ps().put(K_PERM, g.toJson(def));
    }
  }
  @Override public List<String> getCategories(){
    Object raw=ps().get(K_CATS); if(raw==null) return new ArrayList<>(); String s=raw.toString(); String[] arr=g.fromJson(s, String[].class); return new ArrayList<>(Arrays.asList(arr));
  }
  @Override public void addCategory(String name){
    if (name==null||name.trim().isEmpty()) return; List<String> l=getCategories(); if(!l.contains(name)) { l.add(name); ps().put(K_CATS, g.toJson(l)); }
  }
  @Override public void removeCategory(String name){
    List<String> l=getCategories(); if(l.removeIf(x->x.equalsIgnoreCase(name))) ps().put(K_CATS, g.toJson(l));
  }
  @Override public Map<String, Boolean> getPermissions(){
    Object raw=ps().get(K_PERM); if (raw==null) return new HashMap<>(); return g.fromJson(raw.toString(), Map.class);
  }
  @Override public void setPermissions(Map<String, Boolean> perms){ if(perms==null) return; ps().put(K_PERM, g.toJson(perms)); }
}
