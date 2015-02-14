package net.kodleeshare.rsbot.scripts.burthopesoftclayandurns.modulepackages;

import net.kodleeshare.rsbot.script.ActionType;
import net.kodleeshare.rsbot.script.modulescript.ClientContext;
import net.kodleeshare.rsbot.script.modulescript.Module;
import net.kodleeshare.rsbot.script.modulescript.module.SynchronousModule;
import net.kodleeshare.rsbot.script.modulescript.module.packages.BiasedPackage;
import net.kodleeshare.rsbot.script.modulescript.module.packages.SequentialPackage;
import net.kodleeshare.rsbot.scripts.burthopesoftclayandurns.Banking;
import net.kodleeshare.system.Utilities.MinMaxPair;

/**
 * This class opens the bank and deposits inventory
 */
public class BankItems extends SequentialPackage<Module> {
	public static final int VIEW_DISTANCE = 10;
	private static final MinMaxPair TIME_BANK_IN_VIEW = new MinMaxPair(1200, 1500);
	private static final MinMaxPair TIME_OPEN_BANK = new MinMaxPair(1600, 2000);
	private static final MinMaxPair TIME_DEPOSIT = new MinMaxPair(1000, 1300);
	
	public BankItems(ClientContext ctx) {
		super(ctx);
		this
		//Make sure bank is in view
		.install(new SynchronousModule(this.ctx, TIME_BANK_IN_VIEW) {
			{
				this.blockedActionTypes = new ActionType[] { ActionType.CAMERA };
				this.moduleActionTypes = new ActionType[] { ActionType.CAMERA };
			}
			@Override protected void syncRun() {
				this.ctx.camera.turnTo(this.ctx.npcs.select().id(Banking.ID_BANK_TELLER).peek());
			}
			@Override protected boolean waitFor() { return true; }
			@Override protected boolean waitUntil() { 
				return this.ctx.npcs.select().id(Banking.ID_BANK_TELLER).peek().inViewport();
			}
			@Override public boolean canRun() {
				return !this.ctx.npcs.select().id(Banking.ID_BANK_TELLER).isEmpty()
						&& !this.ctx.npcs.peek().inViewport()
						&& !this.ctx.bank.opened();
			}
		})
		
		//Open da bank
		.install(new BiasedPackage<SynchronousModule>(this.ctx) {
			{
				this
				//Use the booth
				.install(new SynchronousModule(this.ctx, TIME_OPEN_BANK) {
					@Override protected void syncRun() {
						this.ctx.objects.select().id(Banking.ID_BANK_BOOTH).poll().interact("Bank");
					}
					@Override protected boolean waitFor() { return true; }
					@Override protected boolean waitUntil() {
						return this.ctx.bank.opened();
					}
					@Override public boolean canRun() {
						return !this.ctx.objects.select().id(Banking.ID_BANK_BOOTH).isEmpty()
								&& this.ctx.objects.peek().inViewport();
					}
				})
				
				//Use the banker
				.install(new SynchronousModule(this.ctx, TIME_OPEN_BANK) {
					@Override protected void syncRun() {
						this.ctx.npcs.select().id(Banking.ID_BANK_TELLER).poll().interact("Bank");
					}
					@Override protected boolean waitFor() { return true; }
					@Override protected boolean waitUntil() {
						return this.ctx.bank.opened();
					}
					@Override public boolean canRun() {
						return !this.ctx.npcs.select().id(Banking.ID_BANK_TELLER).isEmpty()
								&& this.ctx.npcs.peek().inViewport();
					}
				});
			}
			@Override
			public boolean canRun() {
				return !this.ctx.npcs.select().id(Banking.ID_BANK_TELLER).isEmpty()
						&& this.ctx.npcs.peek().inViewport()
						&& !this.ctx.bank.opened();
			}
		})
		
		//Deposit Inventory
		.install(new SynchronousModule(this.ctx, TIME_DEPOSIT) {
			@Override protected void syncRun() {
				this.ctx.bank.depositInventory();
			}
			@Override protected boolean waitFor() { return true; }
			@Override protected boolean waitUntil() {
				return this.ctx.backpack.isEmpty();
			}
			@Override
			public boolean canRun() {
				return !this.ctx.backpack.isEmpty()
						&& this.ctx.bank.opened();
			}
		})
		;
	}

	@Override
	public boolean canRun() {
		return this.ctx.movement.distance(this.ctx.players.local()) <= VIEW_DISTANCE;
	}
}
