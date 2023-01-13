package jackthestripper.songsofsyx_apiary;

import java.io.IOException;

import init.boostable.BOOSTABLE;
import init.boostable.BOOSTABLES;
import settlement.path.finder.SFinderRoomService;
import settlement.room.industry.module.INDUSTRY_HASER;
import settlement.room.industry.module.Industry;
import settlement.room.industry.module.Industry.RoomBoost;
import settlement.room.main.RoomBlueprintIns;
import settlement.room.main.furnisher.Furnisher;
import settlement.room.main.util.RoomInitData;
import snake2d.util.file.FileGetter;
import snake2d.util.file.FilePutter;
import snake2d.util.sets.ArrayList;
import snake2d.util.sets.LIST;
import snake2d.util.sets.LISTE;
import view.sett.ui.room.UIRoomModule;

public class ApiaryRoomMod extends RoomBlueprintIns<ApiaryRoomInstance> implements INDUSTRY_HASER {

	public final ApiaryJob job;
	final Industry productionData;
	public final ApiaryFurnisher constructor;
	final LIST<Industry> indus;
	/**
	 * @param data helpful init data that we don't need to care too much about. It automates reading of the .txt files
	 * @throws IOException 
	 */
	public ApiaryRoomMod (RoomInitData data) throws IOException {
		
		super(0, data, "APIARY_NORMAL", data.m.CATS.MAIN_INDUSTRY.misc);

		constructor = new ApiaryFurnisher(this, data);
		BOOSTABLE skill = BOOSTABLES.ROOMS().pushRoom(this, data.data(), null);
		productionData = new Industry(this, data.data(), new RoomBoost[] {constructor.efficiency}, skill);
		
		job = new ApiaryJob(this);
		indus = new ArrayList<>(productionData);
	}

	@Override
	public Furnisher constructor() {
		return constructor;
	}

	@Override
	protected void update(float ds) {
		
	}
	
	@Override
	public SFinderRoomService service(int tx, int ty) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void saveP(FilePutter saveFile){
		productionData.save(saveFile);

	}
	
	@Override
	protected void loadP(FileGetter saveFile) throws IOException{
		productionData.load(saveFile);
	}
	
	@Override
	protected void clearP() {
		productionData.clear();
	}
	
	@Override
	public boolean makesDudesDirty() {
		return true;
	}
	
	@Override
	public void appendView(LISTE<UIRoomModule> mm) {
	
	}
	
	@Override
	public LIST<Industry> industries() {
		return indus;
	}
}



