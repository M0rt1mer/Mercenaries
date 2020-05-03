package mort.mercenaries.api;

/*
public class GuildManager {
    
    private HashMap<Integer,Guild> guildMap;
    
    private HashMap<PlayerEntity,Guild> playerMap;
    
    public GuildManager(){
	guildMap = new HashMap<Integer, Guild>();
	playerMap = new HashMap<PlayerEntity, Guild>();
    }
    
    public Guild getGuild( int id ){
		if( guildMap.containsKey(id) )
		    return guildMap.get(id);
		Guild gld = new Guild(id);
		addGuild(gld);
		return gld;
    }
    
    public Guild getGuild( PlayerEntity plr ){
    	return playerMap.get(plr);
    }
    
    /**
     * Finds the guild for given entity
     * @return guild for ENtityMercenary or EntityPlayer, null otherwise
     *//*
    public Guild getGuild( LivingEntity ent ){
		if( ent instanceof PlayerEntity )
		    return playerMap.get((PlayerEntity)ent);
		if( ent instanceof EntityMercenary)
		    return ((EntityMercenary)ent).getGuild();
		return null;
    }
    
    public void load( World wld, File directory ){
	if( wld.isRemote)
	    return;
	File saveFile = new File( directory, "guilds.dat" );
	try{
		System.out.println( saveFile.getName() );
	    if(!saveFile.exists())
	    	return; //
	    NBTTagCompound wrap = CompressedStreamTools.readCompressed( new FileInputStream(saveFile) );
	    NBTTagList lst = (NBTTagList) wrap.getTag("guildList");
	    for( int i = 0; i < lst.tagCount(); i++){
			Guild gld = new Guild( lst.getCompoundTagAt(i) );
			addGuild(gld);
	    }
	}catch(Exception e){ throw new ReportedException( new CrashReport("Load mercenary file", e) ); }
	if( wld instanceof WorldServer )
	    purge( (WorldServer)wld );
    }
    
    public void save( World wld, File directory ){
		if( wld.isRemote)
		    return;
		File saveFile = new File( directory, "guilds.dat" );
		try{
		    if(!saveFile.exists())
		    	saveFile.createNewFile();
		    NBTTagList lst = new NBTTagList();
		    for( Guild gld : guildMap.values() ){
				if( gld.shouldSave() ){
				    NBTTagCompound cmp = new NBTTagCompound();
				    gld.save( cmp );
				    lst.appendTag( cmp );
				}
		    }
		    NBTTagCompound wrap = new NBTTagCompound();
		    wrap.setTag("guildList", lst);
		    CompressedStreamTools.writeCompressed(wrap, new FileOutputStream( saveFile ));
		}catch(Exception e){ throw new ReportedException( new CrashReport("Saving mercenary file", e) ); }
    }
    
    public void playerConnect( EntityPlayer player ){
	if( guildMap.containsKey( Guild.getPlayerId(player) ) ){
	    Guild gld = guildMap.get( Guild.getPlayerId(player) );
	    gld.owner = player;
	    addGuild(gld);
	}
	else{
	    Guild gld = new Guild( player );
	    addGuild(gld);
	}
    }
    
    public void playerDisconnect( EntityPlayerMP plr ){
	playerMap.get(plr).owner = null;
	playerMap.remove(plr);
    }
    
    public void clear(){
	playerMap.clear();
	guildMap.clear();
    }
    
    public Guild addGuild( Guild gld ){
	if( !guildMap.containsKey(gld.id) )
	    guildMap.put( gld.id, gld );
	if( gld.owner != null && !playerMap.containsKey(gld.owner) )
	    playerMap.put( gld.owner, gld );
	return gld;
    }
    
    /**
     * Checks for obsolete player entries and deletes them. Obsole entry is such an entry, that doesn't have player file
     *//*
    public void purge( WorldServer wld ){
	HashSet<String> names = new HashSet<String>(  Arrays.asList( wld.getSaveHandler().getPlayerNBTManager().getAvailablePlayerDat() ) );
	for( Guild gld : guildMap.values() ){
	    if( !names.contains(gld.ownerName) && !playerMap.containsKey(gld.owner)  )
		guildMap.remove(gld.id);
	}
	
    }
    
}
*/