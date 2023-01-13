package jackthestripper.songsofsyx_apiary;



import game.GAME;
import settlement.main.RenderData;
import settlement.main.SETT;
import settlement.misc.job.JOBMANAGER_HASER;
import settlement.misc.job.JOB_MANAGER;
import settlement.misc.job.SETT_JOB;
import settlement.misc.util.RESOURCE_TILE;
import settlement.room.industry.module.Industry;
import settlement.room.industry.module.ROOM_PRODUCER;
import settlement.room.main.RoomInstance;
import settlement.room.main.TmpArea;
import settlement.room.main.job.JobPositions;
import settlement.room.main.util.RoomInit;
import snake2d.Renderer;
import snake2d.util.datatypes.COORDINATE;
import snake2d.util.rnd.RND;
import util.rendering.ShadowBatch;

final class ApiaryRoomInstance extends RoomInstance implements JOBMANAGER_HASER, ROOM_PRODUCER {
	

	private static final long serialVersionUID = 1L;
	public final JobPositions<ApiaryRoomInstance> jobs;
	private final long[] pData;
	final short sx,sy;
	boolean hasStorage = true;
	int workage = 0;
	
	//protected ApiaryRoomInstance(RoomBlueprintIns<? extends RoomInstance> blueprint, TmpArea area, RoomInit init) {
	ApiaryRoomInstance( ApiaryRoomMod b, TmpArea area, RoomInit init) {
		super(b, area, init);

		int x = -1;
		int y = -1;
		int w = 0;
		
		for (COORDINATE c : body()) {
			if (is(c)) {
				if (SETT.ROOMS().fData.tileData.get(c) == ApiaryFurnisher.B_WORK) {
					
				}else if (SETT.ROOMS().fData.tileData.get(c) == ApiaryFurnisher.B_STORAGE) {
					if (x == -1) {
						x = c.x();
						y = c.y();
					}
				}else if(SETT.TERRAIN().TREES.isTree(c.x(), c.y())) {
					if (w == 0) {
						SETT.ROOMS().data.set(this, c, ApiaryJob.isWork.set(0));
						w+= RND.rInt(4);
					}else {
						w --;
					}
				}
			}
		}
		
		if (x == -1 || y == -1)
			GAME.Error(x + " " + y);
		sx = (short) x;
		sy = (short) y;
		
		
		pData = b.productionData.makeData();
		jobs = new Jobs(this);
		
		jobs.randomize();
		
		employees().maxSet((int)blueprintI().constructor.workers.get(this));
		employees().neededSet((int)blueprintI().constructor.workers.get(this));
		activate();
	}
	
	@Override
	protected boolean render(Renderer r, ShadowBatch shadowBatch, RenderData.RenderIterator it) {
		if (!SETT.ROOMS().fData.item.is(it.tile())) {
			int d = SETT.ROOMS().fData.spriteData.get(it.tile());
			if (d != 0x0F) {
				blueprintI().constructor.sedge.render(r, shadowBatch, d, it, getDegrade(), false);
			}
		}
		
		return super.render(r, shadowBatch, it);
	}


	@Override
	protected void activateAction() {
		
		
	}

	@Override
	protected void deactivateAction() {

		
	}

	@Override
	protected void updateAction(double updateInterval, boolean day, int daycount) {
		
		blueprintI().productionData.updateRoom(this);
		
		if (!active())
			return;
		jobs.searchAgain();
		
	}

	@Override
	protected void dispose() {
		for (COORDINATE c : body()) {
			if (blueprintI().job.storage.get(c.x(), c.y(), this) != null)
				blueprintI().job.storage.dispose();
		}
	}
	
	@Override
	public JOB_MANAGER getWork() {
		return jobs;
	}
	
	@Override
	public ApiaryRoomMod blueprintI() {
		return (ApiaryRoomMod) blueprint();
	}
	
	@Override
	public RESOURCE_TILE resourceTile(int tx, int ty) {
		return blueprintI().job.storage.get(tx, ty, this);
	}
	
	@Override
	public long[] productionData() {
		return pData;
	}
	

	@Override
	public Industry industry() {
		return blueprintI().industries().get(0);
	}
	
	private static class Jobs extends JobPositions<ApiaryRoomInstance>{

		public Jobs(ApiaryRoomInstance ins) {
			super(ins);
			// TODO Auto-generated constructor stub
		}
		private static final long serialVersionUID = 1L;
		@Override
		protected boolean isAndInit(int tx, int ty) {
			return get(tx, ty) != null;
		}
		
		@Override
		protected SETT_JOB get(int tx, int ty) {
			return ins.blueprintI().job.init(tx, ty, ins);
		}
		
		
	}

	@Override
	public int industryI() {
		// TODO Auto-generated method stub
		return 0;
	}



}
