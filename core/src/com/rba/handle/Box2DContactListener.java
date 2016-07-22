package com.rba.handle;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

public class Box2DContactListener implements ContactListener {

	private int platformNum; //

	boolean monsterContact; //

	Array<Body> removeBodies; //

	public Box2DContactListener() {
		super();
		removeBodies = new Array<Body>();
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();

		Fixture fixtureB = contact.getFixtureB();

		// System.out.println(fixtureA.getUserData()+"--->"+fixtureB.getUserData());

		if (fixtureA.getUserData() != null && fixtureA.getUserData().equals("foot")) {
			platformNum++;
		}
		if (fixtureB.getUserData() != null && fixtureB.getUserData().equals("foot")) {
			platformNum++;
		}

		if (fixtureA.getUserData() != null && fixtureA.getUserData().equals("box")) {
//			removeBodies.add(fixtureA.getBody());
		}
		if (fixtureB.getUserData() != null && fixtureB.getUserData().equals("star")) {
			fixtureB.getBody().setUserData("star");
			removeBodies.add(fixtureB.getBody());
		}

		if (fixtureA.getUserData() != null && fixtureA.getUserData().equals("monster")) {
			monsterContact = true;
		}
		if (fixtureB.getUserData() != null && fixtureB.getUserData().equals("monster")) {
			monsterContact = true;
		}
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		Fixture fixtureA = contact.getFixtureA();

		Fixture fixtureB = contact.getFixtureB();

		if (fixtureA == null || fixtureB == null) {
			return;
		}

		if (fixtureA.getUserData() != null && fixtureA.getUserData().equals("foot")) {
			platformNum--;
		}
		if (fixtureB.getUserData() != null && fixtureB.getUserData().equals("foot")) {
			platformNum--;
		}
		
	}

	public boolean isOnPlatform() {
		return platformNum > 0; // �жϸ����Ƿ��뿪����
	}

	public boolean isContactMonster() {
		return monsterContact;
	}

	public Array<Body> getRemoveBodies() {
		return removeBodies;
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}
