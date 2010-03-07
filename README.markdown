# NeoProjectManager
## A project management library that plays on top of Neo4J graph database

Born as a reimplementation of an existing project management application, now it is developed to experiment with the Neo4J graph database and other technologies as well.

### What is it?
Currently it's just a basic domain-model on top of the Neo4J database. The fundamental domain entities are three: _PROJECT_, _TASK_, _RESOURCE_; defined as follows:

- _PROJECT_: A logical collection of tasks, not necessarly dependent on each other, but related a needed steps to reach a goal.
- _TASK_: a unity of work that can't be split in smaller task. If it could be split then it should be replaced by a sub-project that contain those smaller tasks.
- _RESOURCE_: its a... resource that must be provided in order to perform a task.

### Project Philosphy
It was developed following the guidelines found [here](http://wiki.neo4j.org/content/Design_Guide),  and Some other techniques were implemented to eliminate redundant code (i.e. a generic method to return iteratirs like Iterator<T>, where T is a POJO that wraps a Neo4J node).

I'm looking forward to develop a web interface with Vaadin (GWT).

I'd like t rewrite it in Scala, one day.

### LICENSE
The software is relased under the [BSD license](http://www.opensource.org/licenses/bsd-license.php), see LICENSE file.
