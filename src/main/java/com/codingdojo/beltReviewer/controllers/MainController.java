package com.codingdojo.beltReviewer.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codingdojo.beltReviewer.models.Group;
import com.codingdojo.beltReviewer.models.User;
import com.codingdojo.beltReviewer.services.UserService;
import com.codingdojo.beltReviewer.validation.UserValidator;

@Controller
public class MainController{
	
	private final UserService userService;
	private final UserValidator userValidator;
	public MainController(UserService userService,UserValidator userValidator) {
		this.userService = userService;
		this.userValidator=userValidator;
	}
	
	
	
	
	@RequestMapping("/")
	public String homepage(@ModelAttribute("user")User user) {
		
		return "index.jsp";
	}
	
	@RequestMapping( value = "/registration", method=RequestMethod.POST)
	public String registration(@Valid @ModelAttribute("user")User user,BindingResult result,HttpSession session) {
		
		System.out.println("*******************");
		System.out.println(user.getEmail());
		System.out.println("*******************");

//		System.out.println(this.userService.findByEmail(user.getEmail()));
		userValidator.validate(user,result);
		if(result.hasErrors()) {
			return "index.jsp";
		}
		//TODO later after login reg works prevent dupe emails
		
		// create a user with this information
		
		User userObj = this.userService.registerUser(user);
		//get the user that just got created and store their id in session
		session.setAttribute("userid", userObj.getId());
		return "redirect:/dashboard";
		
	}
	
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		 //clear the session
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
		public String login(@RequestParam("email")String email,@RequestParam("password")String password,HttpSession session,RedirectAttributes redirectAttributes) {
			Boolean isConfirmed = this.userService.authenticateUser(email, password);
			
			if(isConfirmed) {
				//if the email password combo is correct,log them in using session and redirect them to the dashboard
				
				//get the user with that email
				User user= this.userService.findByEmail(email);
				
				//put that user id in session
				session.setAttribute("userid", user.getId());
				return "redirect:/dashboard";
			}
			//if login attempt was unsuccessful, flash an error message
			redirectAttributes.addFlashAttribute("error","Invalid login");
			return "redirect:/";			
		}
	
	///////////////////////REST OF BELT STARTS HERE
	@RequestMapping("/dashboard")
	public String dashboard(Model model,HttpSession session) {
		//retrieve the userobject from the db whos id matches the id stored in session
		Long id = (Long)session.getAttribute("userid");
		User loggedinuser = this.userService.findUserById(id);
		
		model.addAttribute("loggedinuser",loggedinuser);
		model.addAttribute("allgroups",this.userService.findAllGroups());
		return "dashboard.jsp";
	}
	
	@RequestMapping("/groups/new")
	public String newGroup(@ModelAttribute("group")Group group, Model model){
//		this.userService.findAllUsers();
		
		model.addAttribute("allusers",this.userService.findAllUsers());
		
		return "newgroup.jsp";
	}
	@RequestMapping(value="/groups/create",method=RequestMethod.POST)
		public String createGroup(@Valid @ModelAttribute("group")Group group,BindingResult result,Model model,HttpSession session) {
		
		if (result.hasErrors()) {
			
			model.addAttribute("allusers",this.userService.findAllUsers());
			//before rendering the template it needs the information passed to it
            return "newgroup.jsp";
            
		}
		//grab the logged in user so we can assign this user to be the groups creator
		Long id = (Long)session.getAttribute("userid");
		User loggedinuser = this.userService.findUserById(id);
		
		group.setCreator(loggedinuser);
		
		this.userService.createGroup(group);
        
            return "redirect:/dashboard";
        }
	@RequestMapping(value="/groups/{id}")
	public String showAGroup(@PathVariable("id")Long id,Model model) {
		
		//need to get a group from the database
		this.userService.findAGroup(id);
		
		model.addAttribute("groupToShow",this.userService.findAGroup(id));
		
		return "showgroup.jsp";
	}
	
	//variables in the urls need to be catched with the @PathVariable annotation that accepts id and stored in a datatype:Long, and given a variable name
	//We pass information to our template in order for the form to prepopulate we do that with Model model 
	@RequestMapping("/edit/{id}")
	public String editGroup(@PathVariable("id") Long id, Model model) {
		
		model.addAttribute("group",this.userService.findAGroup(id));
		model.addAttribute("allusers",this.userService.findAllUsers());

		return "editform.jsp";
	}
	
	@RequestMapping(value="groups/update/{id}",method=RequestMethod.POST)
	public String updateGroup(@PathVariable("id")Long id,@Valid @ModelAttribute("group")Group group,BindingResult result,Model model, HttpSession session) {
		if(result.hasErrors()) {
			//gets all the users to populate the drop down for vps' and sends to html using model.addAttribute
			model.addAttribute("allusers",this.userService.findAllUsers());
			
			return "editform.jsp";
			
		}
		//send the group info to service to update
		//checking to see if the id got passed into our template
	       
	System.out.println("*******************");
	System.out.println(group.getId());
	System.out.println(group.getCreator());
	Long loggedinuserid = (Long)session.getAttribute("userid");
	User loggedinuser = this.userService.findUserById(loggedinuserid);
	
	group.setCreator(loggedinuser);
	System.out.println("*******************");
	
	this.userService.updateAGroup(group);
	
	return "redirect:/dashboard";
		
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteGroup(@PathVariable("id")Long id) {
		Group g = this.userService.findAGroup(id);
		this.userService.deleteAGroup(g);
		
		return "redirect:/dashboard";
	}
	
	
	
  
}

