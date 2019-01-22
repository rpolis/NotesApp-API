package com.udemy;

import com.google.common.collect.ImmutableList;
import com.udemy.core.Boards;
import com.udemy.core.Notes;
import com.udemy.core.User;
import com.udemy.core.UserBoards;

import com.udemy.db.*;
import com.udemy.resources.HelloResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class DropBookmarksApplication extends Application<DropBookmarksConfiguration> {

    private final HibernateBundle<DropBookmarksConfiguration> hibernateBundle =
            new HibernateBundle<DropBookmarksConfiguration>(User.class,Notes.class, Boards.class, UserBoards.class)
            {
                @Override
                public DataSourceFactory getDataSourceFactory(DropBookmarksConfiguration dropBookmarksConfiguration) {
                    return dropBookmarksConfiguration.getDataSourceFactory();
                }
            };

    public static void main(final String[] args) throws Exception
    {
        new DropBookmarksApplication().run(args);
    }

    @Override
    public String getName() {
        return "DropBookmarks";
    }

    @Override
    public void initialize(final Bootstrap<DropBookmarksConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(final DropBookmarksConfiguration configuration,
                    final Environment environment)
    {
        final UserDAO dao = new UserDAO(hibernateBundle.getSessionFactory());
        final NotesDAO ndao = new NotesDAO((hibernateBundle.getSessionFactory()));
        final BoardsDAO bdao = new BoardsDAO((hibernateBundle.getSessionFactory()));
        final UserBoardsDAO ubdao = new UserBoardsDAO((hibernateBundle.getSessionFactory()));
        environment.jersey().register(new HelloResource(dao,ndao,bdao,ubdao));

        final FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORSFilter", CrossOriginFilter.class);
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        filter.setInitParameter("allowedOrigins", "*");
        filter.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        filter.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        // Add URL mapping
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

    }

}
