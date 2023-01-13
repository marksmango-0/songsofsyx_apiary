package jackthestripper.songsofsyx_apiary;

import java.io.IOException;


import script.SCRIPT;
import settlement.room.main.RoomBlueprint;
import settlement.room.main.RoomCreator;
import settlement.room.main.util.RoomInitData;
import util.info.INFO;

public class ApiaryScript implements SCRIPT{

	private final INFO info = new INFO("apiary", "more rooms");
	
	public ApiaryScript(){
		
	}
	
	@Override
	public CharSequence name() {
		return info.name;
	}

	@Override
	public CharSequence desc() {
		return "apiary building";
	}

	@Override
	public void initBeforeGameCreated() {
		new RoomCreator() {
			
			@Override
			public RoomBlueprint createBlueprint(RoomInitData init) throws IOException {
				return new ApiaryRoomMod(init);
			}
		};
	}
	
	@Override
	public SCRIPT_INSTANCE initAfterGameCreated() {
		return new ApiaryScriptInstance();
	}




}
