package com.ddcorp.delegat1on.impl;
import com.atlassian.sal.api.pluginsettings.*; import com.atlassian.jira.component.ComponentAccessor; import com.atlassian.jira.user.*; import com.ddcorp.delegat1on.api.*; import com.google.gson.*; import org.slf4j.*;
import javax.inject.*; import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport; import com.atlassian.plugin.spring.scanner.annotation.component.Scanned; import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import java.util.*; import java.util.stream.*;
@Scanned @ExportAsService({DelegationService.class}) @Named("delegationService")
public class DelegationServiceImpl implements DelegationService {
  private static final Logger log = LoggerFactory.getLogger(DelegationServiceImpl.class);
  private static final String PS_KEY = "com.ddcorp.delegat1on:records";
  @ComponentImport private final PluginSettingsFactory psf;
  private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
  @Inject public DelegationServiceImpl(PluginSettingsFactory psf){ this.psf = psf; }
  private List<DelegationRecord> load(){ Object raw = psf.createGlobalSettings().get(PS_KEY); if (raw==null) return new ArrayList<>(); try{ DelegationRecord[] arr=gson.fromJson(raw.toString(), DelegationRecord[].class); return new ArrayList<>(Arrays.asList(arr)); }catch(Exception e){ log.warn("parse",e); return new ArrayList<>(); } }
  private void save(List<DelegationRecord> list){ psf.createGlobalSettings().put(PS_KEY, gson.toJson(list)); }
  public List<DelegationRecord> all(){ return load(); }
  public DelegationRecord get(long id){ return load().stream().filter(r->r.id==id).findFirst().orElse(null); }
  public DelegationRecord create(DelegationRecord rec){ List<DelegationRecord> l=load(); if(rec.id==0) rec.id=System.currentTimeMillis(); l.add(rec); save(l); return rec; }
  public DelegationRecord update(DelegationRecord rec){ List<DelegationRecord> l=load(); for (int i=0;i<l.size();i++){ if(l.get(i).id==rec.id){ l.set(i,rec); save(l); return rec; } } return null; }
  public boolean delete(long id){ List<DelegationRecord> l=load(); boolean ch=l.removeIf(r->r.id==id); if(ch) save(l); return ch; }
  public List<DelegationRecord> findActiveDelegationsForDelegator(String k, Date w){ if(k==null) return Collections.emptyList(); return load().stream().filter(r->k.equals(r.delegatorKey)&&!r.disabled&&r.isActiveAt(w)).collect(Collectors.toList()); }
  public List<DelegationRecord> findActiveDelegationsForDelegate(String k, Date w){ if(k==null) return Collections.emptyList(); return load().stream().filter(r->k.equals(r.delegateKey)&&!r.disabled&&r.isActiveAt(w)).collect(Collectors.toList()); }
  public boolean isDelegateOf(String dKey, String rKey, Date w){ if(dKey==null||rKey==null) return false; return findActiveDelegationsForDelegator(rKey,w).stream().anyMatch(r->dKey.equals(r.delegateKey)); }
  public List<ApplicationUser> activeDelegatesOf(ApplicationUser delegator, Date w){ if(delegator==null) return Collections.emptyList(); UserManager um=ComponentAccessor.getUserManager(); return findActiveDelegationsForDelegator(delegator.getKey(),w).stream().map(r->um.getUserByKey(r.delegateKey)).filter(Objects::nonNull).collect(Collectors.toList()); }
  public List<ApplicationUser> activeDelegatorsOf(ApplicationUser delegate, Date w){ if(delegate==null) return Collections.emptyList(); UserManager um=ComponentAccessor.getUserManager(); return load().stream().filter(r->!r.disabled&&r.isActiveAt(w)&&delegate.getKey().equals(r.delegateKey)).map(r->um.getUserByKey(r.delegatorKey)).filter(Objects::nonNull).collect(Collectors.toList()); }
}
